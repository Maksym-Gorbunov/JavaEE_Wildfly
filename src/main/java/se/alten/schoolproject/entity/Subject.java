package se.alten.schoolproject.entity;

import lombok.*;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;


    private static final Logger LOGGER = (Logger) Logger.getLogger(Subject.class.getName());


    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "subject_student",
            joinColumns=@JoinColumn(name="subject_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    private Set<Student> student = new HashSet<>();

    @Transient
    private List<String> students = new ArrayList<>();


    public Subject toEntity(String subjectBody) {
        JsonReader reader = Json.createReader(new StringReader(subjectBody));
        JsonObject jsonObject = reader.readObject();
        Subject subject = new Subject();
        if ( jsonObject.containsKey("title")) {
            subject.setTitle(jsonObject.getString("title"));
        } else {
            subject.setTitle("");
        }

        List<String> tempStudents = new ArrayList<>();
        if (jsonObject.containsKey("students")) {
            JsonArray jsonArray = jsonObject.getJsonArray("students");
            for ( int i = 0; i < jsonArray.size(); i++ ){

                tempStudents.add(jsonArray.get(i).toString().replace("\"", ""));
            }
            subject.setStudents(tempStudents);
        } else {
            subject.setStudents(null);
        }

        return subject;
    }
}
