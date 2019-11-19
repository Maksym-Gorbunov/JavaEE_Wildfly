package se.alten.schoolproject.entity;

import lombok.*;

import javax.json.*;
import javax.persistence.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.*;

@Entity
@Table(name = "teacher")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Teacher implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  //@Column(nullable = false)  // not null
  @Column(name = "id")
  private Long id;

  @Column(name = "forename")
  private String forename;

  @Column(name = "lastname")
  private String lastname;

  @Column(name = "email", unique = true)
  private String email;


  // Bind with Student
  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @JoinTable(name = "teacher_student",
          joinColumns = @JoinColumn(name = "teach_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "stud_id", referencedColumnName = "id"))
  private Set<Student> student = new HashSet<>();
  @Transient
  private List<String> students = new ArrayList<>();


  // Bind with Subject
  @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @JoinTable(name = "teacher_subject",
          joinColumns = @JoinColumn(name = "teach_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "subj_id", referencedColumnName = "id"))
  private Set<Subject> subject = new HashSet<>();
  @Transient
  private List<String> subjects = new ArrayList<>();


  public Teacher(String forename, String lastname, String email) {
    this.forename = forename;
    this.lastname = lastname;
    this.email = email;
  }


  public Teacher toEntity(String teacherModel) {

    List<String> studentEmailList = new ArrayList<>();
    List<String> subjectTitleList = new ArrayList<>();

    JsonReader reader = Json.createReader(new StringReader(teacherModel));
    JsonObject jsonObject = reader.readObject();
    Teacher teacher = new Teacher();
    if (jsonObject.containsKey("forename")) {
      teacher.setForename(jsonObject.getString("forename"));
    } else {
      teacher.setForename("");
    }
    if (jsonObject.containsKey("lastname")) {
      teacher.setLastname(jsonObject.getString("lastname"));
    } else {
      teacher.setLastname("");
    }
    if (jsonObject.containsKey("email")) {
      teacher.setEmail(jsonObject.getString("email"));
    } else {
      teacher.setEmail("");
    }

    if (jsonObject.containsKey("students")) {
      JsonArray studentJsonArray = jsonObject.getJsonArray("students");
      for (int i = 0; i < studentJsonArray.size(); i++) {
        studentEmailList.add(studentJsonArray.get(i).toString().replace("\"", ""));
        teacher.setStudents(studentEmailList);
      }
    } else {
      teacher.setStudents(null);
    }

    if (jsonObject.containsKey("subjects")) {
      JsonArray subjectJsonArray = jsonObject.getJsonArray("subjects");
      for (JsonValue s : subjectJsonArray) {
        subjectTitleList.add(s.toString().replace("\"", ""));
        teacher.setSubjects(subjectTitleList);
      }
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
