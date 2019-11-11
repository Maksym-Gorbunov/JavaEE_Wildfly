package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.rest.StudentController;
import se.alten.schoolproject.transaction.StudentTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

  private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());
  private Student student = new Student();
  private StudentModel studentModel = new StudentModel();

  @Inject
  StudentTransactionAccess studentTransactionAccess;

  @Override
  public List listAllStudents() {
    return studentTransactionAccess.listAllStudents();
  }

  @Override
  public StudentModel addStudent(String studentBody) {
    Student studentToAdd = student.toEntity(studentBody);
    boolean checkForEmptyVariables = Stream.of(studentToAdd.getForename(), studentToAdd.getLastname(), studentToAdd.getEmail()).anyMatch(String::isBlank);

    if (checkForEmptyVariables) {
      studentToAdd.setForename("empty");
      return studentModel.toModel(studentToAdd);
    } else {
      studentTransactionAccess.addStudent(studentToAdd);
      return studentModel.toModel(studentToAdd);
    }
  }

  @Override
  public StudentModel removeStudent(String studentEmail) {
    Student removedstudent = studentTransactionAccess.removeStudent(studentEmail);
    return studentModel.toModel(removedstudent);
  }

  @Override
  public void updateStudent(String forename, String lastname, String email) {
    studentTransactionAccess.updateStudent(forename, lastname, email);
  }

  @Override
  public StudentModel updateStudentPartial(String studentBody) {

    Student studentToUpdate = student.toEntity(studentBody);
    boolean emptyBody =
            Stream.of(studentToUpdate.getForename(),
                    studentToUpdate.getLastname(),
                    studentToUpdate.getEmail())
                    .allMatch(String::isBlank);
    if (emptyBody) {
      studentToUpdate.setForename("empty");
      return studentModel.toModel(studentToUpdate);
    } else {
      studentTransactionAccess.updateStudentPartial(studentToUpdate);
      return studentModel.toModel(studentToUpdate);
    }
  }

  @Override
  public List<StudentModel> findStudentByName(String forename) {
    List<Student> foundedStudents = studentTransactionAccess.findStudentByName(forename);
    List<StudentModel> studentModels = new ArrayList<>();
    for(Student s : foundedStudents){
      studentModels.add(studentModel.toModel(s));
    }
    return studentModels;
  }
}
