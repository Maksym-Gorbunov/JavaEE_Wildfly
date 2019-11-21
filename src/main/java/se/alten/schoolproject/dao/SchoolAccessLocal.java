package se.alten.schoolproject.dao;

import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SchoolAccessLocal {

  /*-------------------- Subject start ------------------------*/

  List<SubjectModel> findAllSubjectsByTitleList(String titleList);

  List getSubjects();

  SubjectModel addSubject(String subjectModel);

  String deleteSubject(String title);

  SubjectModel findSubjectByTitle(String title);

  /*--------------------- Subject end -------------------------*/



  /*-------------------- Student start ------------------------*/

  List listAllStudents() throws Exception;

  StudentModel addStudent(String studentModel);

  StudentModel deleteStudent(String student);

  StudentModel updateStudent(String forename, String lastname, String email);

  StudentModel updateStudentPartial(String studentModel);

  List<StudentModel> findStudentsByName(String forename);

  StudentModel findStudentByEmail(String email);

  /*--------------------- Student end -------------------------*/





}
