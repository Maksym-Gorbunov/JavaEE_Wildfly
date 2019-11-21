package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

  private static final Logger LOGGER = (Logger) Logger.getLogger(SchoolDataAccess.class.getName());

  private Subject subject = new Subject();
  private SubjectModel subjectModel = new SubjectModel();
  @Inject
  SubjectTransactionAccess subjectTA;

  private Student student = new Student();
  private StudentModel studentModel = new StudentModel();
  @Inject
  StudentTransactionAccess studentTA;



  //////////////////////////////////// Subject start //////////////////////////////////////////

  @Override
  public List<SubjectModel> getSubjects() {
    LOGGER.info("SDA: getSubjects()");
    List<Subject> dbResponse = subjectTA.getSubjects();
    return subjectModel.toModelList(dbResponse);
  }


  @Override
  public SubjectModel addSubject(String subjectBody) {
    LOGGER.info("SDA: addSubject()");
    if (subjectBody.isBlank()) {
      return new SubjectModel("empty");
    }
    Subject subj = subject.toEntity(subjectBody);
    Subject dbResponse = subjectTA.addSubject(subj);
    return this.subjectModel.toModel(dbResponse);
  }


  @Override
  public String deleteSubject(String title) {
    LOGGER.info("SDA: deleteSubject()");
    if ((title == null) || (title.isBlank())) {
      return "empty";
    }
    return subjectTA.deleteSubject(title);
  }


  @Override
  public SubjectModel findSubjectByTitle(String title) {
    if ((title == null) || (title.isEmpty())) {
      return null;
    }
    Subject dbResponse = subjectTA.findSubjectByTitle(title);
    return subjectModel.toModel(dbResponse);
  }


  @Override
  public List<SubjectModel> findAllSubjectsByTitleList(String titleListBody) {
    if ((titleListBody == null) || (titleListBody.isEmpty())) {
      return null;
    }

    List<String> titleList = new ArrayList<>();

    JsonReader reader = Json.createReader(new StringReader(titleListBody));
    JsonObject jsonObject = reader.readObject();

    if (jsonObject.containsKey("subjects")) {
      JsonArray jsonArray = jsonObject.getJsonArray("subjects");
      for(JsonValue title : jsonArray){
        titleList.add(title.toString().replace("\"", ""));
      }
    }

    if (titleList.size() > 0) {
      List<SubjectModel> models = new ArrayList<>();
      List<Subject> dbResponse = subjectTA.findAllSubjectsByTitleList(titleList);
      for (Subject subj : dbResponse) {
        models.add(subjectModel.toModel(subj));
      }
      return models;
    }
    return null;
  }

  //////////////////////////////////// Subject end //////////////////////////////////////////




  //////////////////////////////////// Student start //////////////////////////////////////////

  @Override
  public List listAllStudents() {
    System.out.println("getStudents() - SDA");
    List<StudentModel> sm = studentModel.toModelList(studentTA.getStudents());
    return sm;
  }


  @Override
  public StudentModel addStudent(String newStudent) {
    Student studentToAdd = student.toEntity(newStudent);
    boolean checkForEmptyVariables = Stream.of(studentToAdd.getForename(), studentToAdd.getLastname(), studentToAdd.getEmail()).anyMatch(String::isBlank);
    if (checkForEmptyVariables) {
      studentToAdd.setForename("empty");
      return studentModel.toModel(studentToAdd);
    } else {
      studentTA.addStudent(studentToAdd);
      List<Subject> subjects = subjectTA.getSubjectByName(studentToAdd.getSubjects());
      subjects.forEach(sub -> {
        studentToAdd.getSubject().add(sub);
      });
      return studentModel.toModel(studentToAdd);
    }
  }


  @Override
  public StudentModel deleteStudent(String studentEmail) {
    Student removedstudent = studentTA.deleteStudent(studentEmail);
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
      Student temp = studentTA.updateStudent(forename, lastname, email);
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
      studentTA.updateStudentPartial(studentToUpdate);
      return studentModel.toModel(studentToUpdate);
    }
  }


  @Override
  public List<StudentModel> findStudentsByName(String forename) {
    List<Student> foundedStudents = studentTA.findStudentsByName(forename);
    List<StudentModel> studentModels = new ArrayList<>();
    for (Student s : foundedStudents) {
      studentModels.add(studentModel.toModel(s));
    }
    return studentModels;
  }


  public StudentModel findStudentByEmail(String email) {
    Student foundedStudent = studentTA.findStudentByEmail(email);
    return studentModel.toModel(foundedStudent);
  }


  ///////////////////////////////////// Student end ///////////////////////////////////////////


}
