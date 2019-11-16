package se.alten.schoolproject.entity;

import lombok.*;
import javax.json.*;
import javax.persistence.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.*;

@Entity
@Table(name="student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "forename")
    private String forename;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "student_subject",
                joinColumns=@JoinColumn(name="stud_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "subj_id", referencedColumnName = "id"))

    private Set<Subject> subject = new HashSet<>();

    @Transient
    private List<String> subjects = new ArrayList<>();

    public Student(String forename, String lastname, String email) {
        this.forename = forename;
        this.lastname = lastname;
        this.email = email;
    }



    public Student toEntity(String studentModel) {

        List<String> temp = new ArrayList<>();

        JsonReader reader = Json.createReader(new StringReader(studentModel));
        JsonObject jsonObject = reader.readObject();
        Student student = new Student();
        if ( jsonObject.containsKey("forename")) {
            student.setForename(jsonObject.getString("forename"));
        } else {
            student.setForename("");
        }
        if ( jsonObject.containsKey("lastname")) {
            student.setLastname(jsonObject.getString("lastname"));
        } else {
            student.setLastname("");
        }
        if ( jsonObject.containsKey("email")) {
            student.setEmail(jsonObject.getString("email"));
        } else {
            student.setEmail("");
        }

        if (jsonObject.containsKey("subjects")) {
            JsonArray jsonArray = jsonObject.getJsonArray("subjects");
            for ( int i = 0; i < jsonArray.size(); i++ ){
                temp.add(jsonArray.get(i).toString().replace("\"", ""));
                student.setSubjects(temp);
            }
        } else {
            student.setSubjects(null);
        }

        return student;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return email.equals(student.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
