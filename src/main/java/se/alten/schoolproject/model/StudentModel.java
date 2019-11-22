package se.alten.schoolproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.alten.schoolproject.entity.Student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentModel {

  //private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());


  private String forename;
  private String lastname;
  private String email;

  private Set<String> subjects = new HashSet<>();

  public StudentModel toModel(Student studentToAdd) {
    StudentModel studentModel = new StudentModel();

    switch (studentToAdd.getForename()) {
      case "empty":
        studentModel.setForename("empty");
        return studentModel;
      case "duplicate":
        studentModel.setForename("duplicate");
        return studentModel;
      default:
        studentModel.setForename(studentToAdd.getForename());
        studentModel.setLastname(studentToAdd.getLastname());
        studentModel.setEmail(studentToAdd.getEmail());


        studentToAdd.getJoinedSubjects().forEach(subj -> {
          studentModel.subjects.add(subj.getTitle());
        });


        return studentModel;
    }
  }

  public List<StudentModel> toModelList(List<Student> students) {
    List<StudentModel> studentModels = new ArrayList<>();
    students.forEach(student -> {
      StudentModel sm = toModel(student);
      studentModels.add(sm);
    });
    return studentModels;
  }
}
