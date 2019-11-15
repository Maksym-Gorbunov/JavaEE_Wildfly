package se.alten.schoolproject.dao;

import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SchoolAccessLocal {

    List listAllStudents();

    StudentModel addStudent(String studentModel);

    void removeStudent(String student);

    void updateStudent(String forename, String lastname, String email);

    void updateStudentPartial(String studentModel);

    List listAllSubjects();

    SubjectModel addSubject(String subjectModel);
}
