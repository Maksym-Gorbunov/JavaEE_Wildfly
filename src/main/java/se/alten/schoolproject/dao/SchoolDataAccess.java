package se.alten.schoolproject.dao;

import org.apache.log4j.Logger;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.model.TeacherModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;
import se.alten.schoolproject.transaction.TeacherTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

  private static final Logger LOGGER = Logger.getLogger(SchoolDataAccess.class);

  private Subject subject = new Subject();
  private SubjectModel subjectModel = new SubjectModel();
  @Inject
  SubjectTransactionAccess subjectTA;

  private Student student = new Student();
  private StudentModel studentModel = new StudentModel();
  @Inject
  StudentTransactionAccess studentTA;

  private Teacher teacher = new Teacher();
  private TeacherModel teacherModel = new TeacherModel();
  @Inject
  TeacherTransactionAccess teacherTA;


  //////////////////////////////////// Subject start //////////////////////////////////////////

  @Override
  public List<SubjectModel> getSubjects() {
    LOGGER.error("SDA: getSubjects()");
    List<Subject> dbResponse = subjectTA.getSubjects();
    return subjectModel.toModelList(dbResponse);
  }


  @Override
  public SubjectModel addSubject(String subjectBody) {
    LOGGER.info("SDA: addSubject()");
    if (subjectBody.isBlank()) {
      return new SubjectModel("empty");
    }
    Subject subjectToAdd = subject.toEntity(subjectBody);
    Subject dbResponse = subjectTA.addSubject(subjectToAdd);


//    // bind subject with transientStudents, ?? not need in add method
//    if (subjectToAdd.getTransientStudents() != null) {
//      List<Student> students = studentTA.getStudentsByEmail(subjectToAdd.getTransientStudents());
//      students.forEach(stud -> {
//        subjectToAdd.getJoinedStudents().add(stud);
//      });
//    }

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
      for (JsonValue title : jsonArray) {
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


  @Override
  public String addStudentToSubject(String subjectTitle, String studentBody) {

    JsonReader reader = Json.createReader(new StringReader(studentBody));
    JsonObject jsonObject = reader.readObject();
    Subject subjectForUpdate = subjectTA.getSubjectByTitle(subjectTitle);

    if ((subjectForUpdate != null) && (subjectForUpdate.getTitle().equals(subjectTitle))) {
      if (jsonObject.containsKey("student")) {
        JsonValue jsonValue = jsonObject.getValue("/student");
        String email = jsonValue.toString().replace("\"", "");

        //bind
        Student studentToJoin = studentTA.getStudentByEmail(email);
        subjectForUpdate.getJoinedStudents().add(studentToJoin);

      }
    }

    return null;
  }


  //////////////////////////////////// Subject end //////////////////////////////////////////


  //////////////////////////////////// Student start //////////////////////////////////////////

  @Override
  public List<StudentModel> getStudents() {
    System.out.println("getTransientStudents() - SDA");
    List<Student> dbResponse = studentTA.getStudents();
    return studentModel.toModelList(dbResponse);
  }


  @Override
  public StudentModel addStudent(String studentBody) {
    Student studentToAdd = student.toEntity(studentBody);
    boolean checkForEmptyVariables = Stream.of(studentToAdd.getForename(), studentToAdd.getLastname(), studentToAdd.getEmail()).anyMatch(String::isBlank);
    if (checkForEmptyVariables) {
      studentToAdd.setForename("empty");
      return studentModel.toModel(studentToAdd);
    } else {
      Student dbResponse = studentTA.addStudent(studentToAdd);
      return studentModel.toModel(dbResponse);
    }
  }


  @Override
  public String deleteStudent(String email) {
    LOGGER.info("SDA: deleteSubject()");
    if ((email == null) || (email.isBlank())) {
      return "empty";
    }
    return studentTA.deleteStudent(email);
  }


//  @Override
//  public StudentModel updateStudent(String forename, String lastname, String email) {
//    Student studentToUpdate = new Student(forename, lastname, email);
//    boolean emptyField = Stream.of(forename, lastname, email)
//            .anyMatch(String::isBlank);
//    if (emptyField) {
//      studentToUpdate.setForename("empty");
//      return studentModel.toModel(studentToUpdate);
//    } else {
//      Student temp = studentTA.updateStudent(forename, lastname, email);
//      return studentModel.toModel(studentToUpdate);
//    }
//  }


  //  @Override
//  public StudentModel updateStudentPartial(String studentBody) {
//    Student studentToUpdate = student.toEntity(studentBody);
//    boolean emptyBody =
//            Stream.of(studentToUpdate.getForename(),
//                    studentToUpdate.getLastname(),
//                    studentToUpdate.getEmail())
//                    .allMatch(String::isBlank);
//    if (emptyBody) {
//      studentToUpdate.setForename("empty");
//      return studentModel.toModel(studentToUpdate);
//    } else {
//      studentTA.updateStudentPartial(studentToUpdate);
//      return studentModel.toModel(studentToUpdate);
//    }
//  }

  @Override
  public StudentModel findStudentByEmail(String email) {
    Student dbResponse = studentTA.findStudentByEmail(email);
    return studentModel.toModel(dbResponse);
  }

  @Override
  public List<StudentModel> findStudentsByForename(String forename) {
    List<Student> dbResponse = studentTA.findStudentsByName(forename);
    List<StudentModel> studentModels = new ArrayList<>();
    if ((dbResponse != null) && (dbResponse.size() > 0)) {
      for (Student stud : dbResponse) {
        studentModels.add(studentModel.toModel(stud));
      }
      return studentModels;
    }
    return null;
  }

  ///////////////////////////////////// Student end ///////////////////////////////////////////


  //////////////////////////////////// Teacher start //////////////////////////////////////////

  @Override
  public List<TeacherModel> getTeachers() {
    System.out.println("getTeachers() - SDA");
    List<Teacher> dbResponse = teacherTA.getTeachers();
    return teacherModel.toModelList(dbResponse);
  }


  @Override
  public TeacherModel addTeacher(String teacherBody) {
    LOGGER.info("SDA: addTeacher()");
    Teacher teacherToAdd = teacher.toEntity(teacherBody);
    boolean checkForEmptyVariables = Stream.of(teacherToAdd.getForename(), teacherToAdd.getLastname(), teacherToAdd.getEmail())
            .anyMatch(String::isBlank);
    if (checkForEmptyVariables) {
      teacherToAdd.setForename("empty");
      return teacherModel.toModel(teacherToAdd);
    } else {
      Teacher dbResponse = teacherTA.addTeacher(teacherToAdd);
      return teacherModel.toModel(dbResponse);
    }
  }


  @Override
  public String deleteTeacher(String email) {
    LOGGER.info("SDA: deleteTeacher()");
    if ((email == null) || (email.isBlank())) {
      return "empty";
    }
    return teacherTA.deleteTeacher(email);
  }


//TEACHER UPDATE
//  @Override
//  public StudentModel updateStudent(String forename, String lastname, String email) {
//    Student studentToUpdate = new Student(forename, lastname, email);
//    boolean emptyField = Stream.of(forename, lastname, email)
//            .anyMatch(String::isBlank);
//    if (emptyField) {
//      studentToUpdate.setForename("empty");
//      return studentModel.toModel(studentToUpdate);
//    } else {
//      Student temp = studentTA.updateStudent(forename, lastname, email);
//      return studentModel.toModel(studentToUpdate);
//    }
//  }


//TEACHER UPDATE partial
  //  @Override
//  public StudentModel updateStudentPartial(String studentBody) {
//    Student studentToUpdate = student.toEntity(studentBody);
//    boolean emptyBody =
//            Stream.of(studentToUpdate.getForename(),
//                    studentToUpdate.getLastname(),
//                    studentToUpdate.getEmail())
//                    .allMatch(String::isBlank);
//    if (emptyBody) {
//      studentToUpdate.setForename("empty");
//      return studentModel.toModel(studentToUpdate);
//    } else {
//      studentTA.updateStudentPartial(studentToUpdate);
//      return studentModel.toModel(studentToUpdate);
//    }
//  }

  @Override
  public TeacherModel findTeacherByEmail(String email) {
    Teacher dbResponse = teacherTA.findTeacherByEmail(email);
    return teacherModel.toModel(dbResponse);
  }

  @Override
  public List<TeacherModel> findTeachersByForename(String forename) {
    List<Teacher> dbResponse = teacherTA.findTeachersByForename(forename);
    List<TeacherModel> teacherModels = new ArrayList<>();
    if ((dbResponse != null) && (dbResponse.size() > 0)) {
      for (Teacher teach : dbResponse) {
        teacherModels.add(teacherModel.toModel(teach));
      }
      return teacherModels;
    }
    return null;
  }

  ///////////////////////////////////// Teacher end ///////////////////////////////////////////

}
