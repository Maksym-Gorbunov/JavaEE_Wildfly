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

  private String forename;
  private String lastname;
  private String email;

  private Set<String> subjects = new HashSet<>();

  public TeacherModel toModel(Teacher teacherObject) {
    TeacherModel teacherModel = new TeacherModel();

    switch (teacherObject.getForename()) {
      case "empty":
        teacherModel.setForename("empty");
        return teacherModel;
      case "duplicate":
        teacherModel.setForename("duplicate");
        return teacherModel;
      default:
        teacherModel.setForename(teacherObject.getForename());
        teacherModel.setLastname(teacherObject.getLastname());
        teacherModel.setEmail(teacherObject.getEmail());

        teacherObject.getJoinedSubjects().forEach(subj -> {
          teacherModel.subjects.add(subj.getTitle());
        });

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
