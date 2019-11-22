package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StudentTransactionAccess {
  List getStudents();

  Student addStudent(Student studentToAdd);

  String deleteStudent(String student);

  Student updateStudent(String forename, String lastname, String email);

  Student updateStudentPartial(Student studentToUpdate);

  List<Student> findStudentsByName(String forename);

  Student findStudentByEmail(String email);

  List<Student> getStudentsByEmail(List<String> studentsJoin);

  Student getStudentByEmail(String email);
}
