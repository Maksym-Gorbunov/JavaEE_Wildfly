package se.alten.schoolproject.dao;

import se.alten.schoolproject.model.StudentModel;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SchoolAccessLocal {

    List listAllStudents() throws Exception;

    StudentModel addStudent(String studentModel);

    StudentModel removeStudent(String student);

    StudentModel updateStudent(String forename, String lastname, String email);

    StudentModel updateStudentPartial(String studentModel);

    List<StudentModel> findStudentsByName(String forename);

    StudentModel findStudentByEmail(String email);
}
