package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Default
public class StudentTransaction implements StudentTransactionAccess {

  //private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());

  @PersistenceContext(unitName = "school")
  private EntityManager em;


  @Override
  public List<Student> getStudents() {
    System.out.println("getStudents() - Transaction");
    Query query = em.createQuery("SELECT s FROM Student s");
    List<Student> temp = query.getResultList();
//    List<Student> result = temp.stream()
//            .distinct()
//            .collect(Collectors.toList());
//    return result;
    return temp;
  }


  @Override
  public Student addStudent(Student studentToAdd) {
    em.persist(studentToAdd);
    em.flush();
    return studentToAdd;
  }


  @Override
  public String deleteStudent(String email) {
    Query query = em.createQuery("DELETE FROM Student s WHERE s.email = :email");
    query.setParameter("email", email);
    int rowsDeleted = query.executeUpdate();
    if (rowsDeleted < 1) {
      return "";
    }
    return email;
  }


  @Override
  public Student updateStudent(String forename, String lastname, String email) {
    Student studentToUpdate = (Student) em
            .createQuery("SELECT s FROM Student s WHERE s.email = :email")
            .setParameter("email", email).getSingleResult();
    Query query = em.createQuery("UPDATE Student SET forename = :forename, lastname = :lastname WHERE email = :email");
    query.setParameter("forename", forename)
            .setParameter("lastname", lastname)
            .setParameter("email", studentToUpdate.getEmail())
            .executeUpdate();
    return studentToUpdate;
  }

  @Override
  public Student updateStudentPartial(Student student) {
    Student studentToUpdate = (Student) em
            .createQuery("SELECT s FROM Student s WHERE s.email = :email")
            .setParameter("email", student.getEmail()).getSingleResult();
    Query query = em.createQuery("UPDATE Student SET forename = :forename, lastname = :lastname WHERE email = :email");
    query.setParameter("forename", student.getForename())
            .setParameter("lastname", student.getLastname())
            .setParameter("email", studentToUpdate.getEmail())
            .executeUpdate();
    return studentToUpdate;
  }


  @Override
  public List<Student> findStudentsByName(String forename) {
    List<Student> foundedStudents = em.createQuery("SELECT s FROM Student s WHERE s.forename = :forename")
            .setParameter("forename", forename).getResultList();
    return foundedStudents;
  }


  @Override
  public Student findStudentByEmail(String email) {
    Student foundedStudent = em.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class)
            .setParameter("email", email).getSingleResult();
    return foundedStudent;
  }


  @Override
  public List<Student> getStudentsByEmail(List<String> emails) {
    String queryStr = "SELECT stud FROM Student stud WHERE stud.email IN :emails";
    TypedQuery<Student> query = em.createQuery(queryStr, Student.class);
    query.setParameter("emails", emails);
    return query.getResultList();
  }
}
