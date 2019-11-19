package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;

import java.util.List;

public interface TeacherTransactionAccess {
  public List listAllTeachers();

  Teacher addTeacher(Teacher teacherToAdd);

  String deleteTeacher(String mail);
}
