package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StudentTransactionAccess {
    List listAllStudents();
    Student addStudent(Student studentToAdd);
    Student deleteStudent(String student);
    Student updateStudent(String forename, String lastname, String email);
    Student updateStudentPartial(Student studentToUpdate);
    List<Student> findStudentsByName(String forename);
    Student findStudentByEmail(String email);
}
