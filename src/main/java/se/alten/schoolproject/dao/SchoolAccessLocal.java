package se.alten.schoolproject.dao;

import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.model.TeacherModel;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SchoolAccessLocal {

  /*-------------------- Subject start ------------------------*/

  List getSubjects();

  SubjectModel addSubject(String subjectModel);

  String deleteSubject(String title);

  List<SubjectModel> findAllSubjectsByTitleList(String titleList);

  SubjectModel findSubjectByTitle(String title);

  String addStudentToSubject(String subjectTitle, String studentsBody);

  /*--------------------- Subject end -------------------------*/




  /*-------------------- Student start ------------------------*/

  List<StudentModel> getStudents();

  List<TeacherModel> getTeachers();

  StudentModel addStudent(String newStudent);

  TeacherModel addTeacher(String teacherBody);

  String deleteStudent(String studentEmail);

  String deleteTeacher(String email);

  StudentModel findStudentByEmail(String email);

  TeacherModel findTeacherByEmail(String email);

  List<StudentModel> findStudentsByForename(String forename);

  List<TeacherModel> findTeachersByForename(String forename);



  /*--------------------- Student end -------------------------*/











}
