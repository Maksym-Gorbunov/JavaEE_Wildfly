package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Teacher;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TeacherTransactionAccess {
  List getTeachers();
  Teacher addTeacher(Teacher teacher);
  String deleteTeacher(String email);
  Teacher updateTeacher(String forename, String lastname, String email);
  Teacher updateTeacherPartial(Teacher teacher);
  List<Teacher> findTeachersByForename(String forename);
  Teacher findTeacherByEmail(String email);
}
