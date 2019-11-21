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

  public StudentModel toModel(Student student) {
    StudentModel studentModel = new StudentModel();

    switch (student.getForename()) {
      case "empty":
        studentModel.setForename("empty");
        return studentModel;
      case "duplicate":
        studentModel.setForename("duplicate");
        return studentModel;
      default:
        studentModel.setForename(student.getForename());
        studentModel.setLastname(student.getLastname());
        studentModel.setEmail(student.getEmail());

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
