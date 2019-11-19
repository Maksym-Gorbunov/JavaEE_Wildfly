package se.alten.schoolproject.dao;

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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

//  private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());


  private Student student = new Student();
  private StudentModel studentModel = new StudentModel();

  private Subject subject = new Subject();
  private SubjectModel subjectModel = new SubjectModel();

  private Teacher teacher = new Teacher();
  private TeacherModel teacherModel = new TeacherModel();

  @Inject
  StudentTransactionAccess studentTA;
  @Inject
  SubjectTransactionAccess subjectTA;
  @Inject
  TeacherTransactionAccess teacherTA;


  @Override
  public List listAllStudents() {
    System.out.println("listAllStudents() - SDA");
    List<StudentModel> sm = studentModel.toModelList(studentTA.listAllStudents());
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

  //////////////////////////////////// Subject //////////////////////////////////////////

  @Override
  public List listAllSubjects() {
    System.out.println("listAllSubjects() - SDA");
    //LOGGER.info("listAllSubjects() - SDA");
    List<SubjectModel> sm = subjectModel.toModelList(subjectTA.listAllSubjects());
    //return subjectTA.listAllSubjects();
    return sm;
  }


  @Override
  public SubjectModel addSubject(String subjectModel) {
    if (subjectModel.isBlank()) {
      return new SubjectModel("empty");
    }
    Subject subjectToAdd = subject.toEntity(subjectModel);
    subjectTA.addSubject(subjectToAdd);
    return this.subjectModel.toModel(subjectToAdd);
  }


  @Override
  public String deleteSubject(String title) {
    if ((title == null) || (title.isBlank())) {
      return "empty";
    }
    return subjectTA.deleteSubject(title);
  }


  @Override
  public List<StudentModel> listStudentsBySubject(String subject) {
    if ((subject == null) || (subject.isBlank())) {
      return null;
    }
    List<StudentModel> studentModels = new ArrayList<>();
    List<Student> students = studentTA.listStudentsBySubject(subject);
    for (Student s : students) {
      studentModels.add(studentModel.toModel(s));
    }
    return studentModels;
  }

  /////////////////////////////// Teacher ////////////////////////////////////////

  @Override
  public List listAllTeachers() {
    System.out.println("listAllTeachers() - SDA");
    List<TeacherModel> tm = teacherModel.toModelList(teacherTA.listAllTeachers());
    return tm;
  }


  @Override
  public TeacherModel addTeacher(String teacherBody) {
    System.out.println("addTeacher() - SDA");
    Teacher teacherToAdd = teacher.toEntity(teacherBody);
    boolean checkForEmptyVariables = Stream.of(teacherToAdd.getForename(), teacherToAdd.getLastname(), teacherToAdd.getEmail()).anyMatch(String::isBlank);
    if (checkForEmptyVariables) {
      teacherToAdd.setForename("empty");
      return teacherModel.toModel(teacherToAdd);
    } else {
      teacherTA.addTeacher(teacherToAdd);

      List<Student> students = studentTA.getStudentsByEmail(teacherToAdd.getStudents());
      students.forEach(stud -> {
        teacherToAdd.getStudent().add(stud);
      });

      List<Subject> subjects = subjectTA.getSubjectByName(teacherToAdd.getSubjects());
      subjects.forEach(subj -> {
        teacherToAdd.getSubject().add(subj);
      });

      return teacherModel.toModel(teacherToAdd);
    }
  }


  @Override
  public String deleteTeacher(String mail) {
    if ((mail == null) || (mail.isBlank())) {
      return "empty";
    }
    return teacherTA.deleteTeacher(mail);
  }
}
