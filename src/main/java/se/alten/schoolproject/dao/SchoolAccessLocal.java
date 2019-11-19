package se.alten.schoolproject.dao;

import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.model.TeacherModel;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SchoolAccessLocal {

  List listAllStudents() throws Exception;

  StudentModel addStudent(String studentModel);

  StudentModel deleteStudent(String student);

  StudentModel updateStudent(String forename, String lastname, String email);

  StudentModel updateStudentPartial(String studentModel);

  List<StudentModel> findStudentsByName(String forename);

  StudentModel findStudentByEmail(String email);


  List listAllSubjects();

  SubjectModel addSubject(String subjectModel);

  String deleteSubject(String title);

  List<StudentModel> listStudentsBySubject(String subject);

  public List listAllTeachers();

  TeacherModel addTeacher(String teacherBody);

  String deleteTeacher(String email);
}
