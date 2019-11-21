package se.alten.schoolproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

  //private static final Logger LOGGER = (Logger) Logger.getLogger(TeacherController.class.getName());


  private String forename;
  private String lastname;
  private String email;

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

        return teacherModel;
    }
  }

  public List<TeacherModel> toModelList(List<Teacher> teachers) {
    List<TeacherModel> teacherModels = new ArrayList<>();
    teachers.forEach(teacher -> {
      TeacherModel sm = toModel(teacher);
      teacherModels.add(sm);
    });
    return teacherModels;
  }
}
