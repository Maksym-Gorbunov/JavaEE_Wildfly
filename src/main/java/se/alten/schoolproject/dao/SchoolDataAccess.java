package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.rest.StudentController;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;

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
  private Subject subject = new Subject();
  private SubjectModel subjectModel = new SubjectModel();

  @Inject
  StudentTransactionAccess studentTransactionAccess;

  @Inject
  SubjectTransactionAccess subjectTransactionAccess;



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
      List<Subject> subjects = subjectTransactionAccess.getSubjectByName(studentToAdd.getSubjects());

      subjects.forEach(sub -> {
        studentToAdd.getSubject().add(sub);
      });
      return studentModel.toModel(studentToAdd);
    }
  }


  @Override
  public StudentModel removeStudent(String studentEmail) {
    Student removedstudent = studentTransactionAccess.removeStudent(studentEmail);
    return studentModel.toModel(removedstudent);
  }


  @Override
  public StudentModel updateStudent(String forename, String lastname, String email) {
    Student studentToUpdate = new Student(forename, lastname, email);
    boolean emptyField = Stream.of(forename, lastname, email)
            .anyMatch(String::isBlank);
    if (emptyField) {
      studentToUpdate.setForename("empty");
      return studentModel.toModel(studentToUpdate);
    } else {
      Student temp = studentTransactionAccess.updateStudent(forename, lastname, email);
      return studentModel.toModel(studentToUpdate);
    }
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
  public List<StudentModel> findStudentsByName(String forename) {
    List<Student> foundedStudents = studentTransactionAccess.findStudentsByName(forename);
    List<StudentModel> studentModels = new ArrayList<>();
    for (Student s : foundedStudents) {
      studentModels.add(studentModel.toModel(s));
    }
    return studentModels;
  }


  public StudentModel findStudentByEmail(String email) {
    Student foundedStudent = studentTransactionAccess.findStudentByEmail(email);
    return studentModel.toModel(foundedStudent);
  }




  @Override
  public List listAllSubjects() {
    return subjectTransactionAccess.listAllSubjects();
  }

  @Override
  public SubjectModel addSubject(String newSubject) {
    Subject subjectToAdd = subject.toEntity(newSubject);
    subjectTransactionAccess.addSubject(subjectToAdd);
    return subjectModel.toModel(subjectToAdd);
  }




}



/*
@Override
  public StudentModel updateStudent(String forename, String lastname, String email) {
    StudentModel studentModelToUpdate = new StudentModel(forename, lastname, email);
    boolean emptyField = Stream.of(forename, lastname, email)
            .anyMatch(String::isBlank);
    if (emptyField) {
      studentModelToUpdate.setForename("empty");
      return studentModelToUpdate;
    } else {
      Student temp = studentTransactionAccess.updateStudent(forename, lastname, email);
      return studentModelToUpdate;
    }
  }
 */

