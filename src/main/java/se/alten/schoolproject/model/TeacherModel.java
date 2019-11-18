package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherModel {

  //private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());


  private String forename;
  private String lastname;
  private String email;
//  private Set<String> students = new HashSet<>();
//  private Set<String> subjects = new HashSet<>();

  public TeacherModel toModel(Teacher teacher) {
    TeacherModel teacherModel = new TeacherModel();

    switch (teacher.getForename()) {
      case "empty":
        teacherModel.setForename("empty");
        return teacherModel;
      case "duplicate":
        teacherModel.setForename("duplicate");
        return teacherModel;
      default:
        teacherModel.setForename(teacher.getForename());
        teacherModel.setLastname(teacher.getLastname());
        teacherModel.setEmail(teacher.getEmail());
//        teacher.getStudent().forEach(student -> {
//          teacherModel.students.add(student.getTitle());
//        });
        return teacherModel;
    }
  }

  public List<TeacherModel> toModelList(List<Teacher> teachers) {

    List<TeacherModel> teacherModels = new ArrayList<>();

    teachers.forEach(teacher -> {
      TeacherModel tm = new TeacherModel();
      tm.forename = teacher.getForename();
      tm.lastname = teacher.getLastname();
      tm.email = teacher.getEmail();
//      teacher.getStudent().forEach(student -> {
//        tm.students.add(student.getTitle());
//      });

      teacherModels.add(tm);
    });
    return teacherModels;
  }
}
