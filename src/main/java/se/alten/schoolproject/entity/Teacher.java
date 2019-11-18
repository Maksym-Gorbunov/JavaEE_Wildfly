package se.alten.schoolproject.entity;

import lombok.*;
import javax.json.*;
import javax.persistence.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.*;

@Entity
@Table(name="teacher")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Teacher implements Serializable {

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
  @JoinTable(name = "teacher_student",
          joinColumns=@JoinColumn(name="teach_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "stud_id", referencedColumnName = "id"))

  private Set<Subject> student = new HashSet<>();

  @Transient
  private List<String> students = new ArrayList<>();

  public Teacher(String forename, String lastname, String email) {
    this.forename = forename;
    this.lastname = lastname;
    this.email = email;
  }



  public Teacher toEntity(String teacherModel) {

    List<String> temp = new ArrayList<>();

    JsonReader reader = Json.createReader(new StringReader(teacherModel));
    JsonObject jsonObject = reader.readObject();
    Teacher teacher = new Teacher();
    if ( jsonObject.containsKey("forename")) {
      teacher.setForename(jsonObject.getString("forename"));
    } else {
      teacher.setForename("");
    }
    if ( jsonObject.containsKey("lastname")) {
      teacher.setLastname(jsonObject.getString("lastname"));
    } else {
      teacher.setLastname("");
    }
    if ( jsonObject.containsKey("email")) {
      teacher.setEmail(jsonObject.getString("email"));
    } else {
      teacher.setEmail("");
    }

    if (jsonObject.containsKey("students")) {
      JsonArray jsonArray = jsonObject.getJsonArray("students");
      for ( int i = 0; i < jsonArray.size(); i++ ){
        temp.add(jsonArray.get(i).toString().replace("\"", ""));
        teacher.setStudents(temp);

        ///////////////// set subjects for each student /////////////


      }
    } else {
      teacher.setStudents(null);
    }

    return teacher;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Teacher teacher = (Teacher) o;

    return email.equals(teacher.email);
  }

  @Override
  public int hashCode() {
    return email.hashCode();
  }
}
