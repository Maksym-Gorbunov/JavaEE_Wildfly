package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Default
public class StudentTransaction implements StudentTransactionAccess {

  //private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());

  @PersistenceContext(unitName = "school")
  private EntityManager em;


  @Override
  public List<Student> listAllStudents() {
    System.out.println("listAllStudents() - Transaction");
    Query query = em.createQuery("SELECT s FROM Student s JOIN FETCH s.subject t");
    List<Student> temp = query.getResultList();
    List<Student> result = temp.stream()
            .distinct()
            .collect(Collectors.toList());
    return result;
  }


  @Override
  public Student addStudent(Student studentToAdd) {
    em.persist(studentToAdd);
    em.flush();
    return studentToAdd;
  }


  @Override
  public Student deleteStudent(String email) {
    Student studentToRemove = new Student();
    Student removedStudent = new Student();

    studentToRemove = (Student) em.createQuery("SELECT s FROM Student s WHERE s.email = :email")
            .setParameter("email", email).getSingleResult();

    removedStudent.setId(studentToRemove.getId());
    removedStudent.setForename(studentToRemove.getForename());
    removedStudent.setLastname(studentToRemove.getLastname());
    removedStudent.setEmail(studentToRemove.getEmail());

    Query query = em.createQuery("DELETE FROM Student s WHERE s.email = :email");
    query.setParameter("email", email)
            .executeUpdate();
    return removedStudent;
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


  public Student findStudentByEmail(String email) {
    Student foundedStudent = em.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class)
            .setParameter("email", email).getSingleResult();
    return foundedStudent;
  }


  @Override
  public List<Student> listStudentsBySubject(String subj) {
      Query query = em.createQuery("SELECT s FROM Student s JOIN FETCH s.subject t WHERE t.title=:subj");
      query.setParameter("subj", subj);
      List<Student> res = query.getResultList();
      return res;
  }


  @Override
  public List<Student> getStudentsByEmail(List<String> emailList) {
    String queryStr = "SELECT stud FROM Student stud WHERE stud.email IN :emailList";
    TypedQuery<Student> query = em.createQuery(queryStr, Student.class);
    query.setParameter("emailList", emailList);
    return query.getResultList();
  }



}



/* JPQL query
    Student foundedStudent = (Student) em.createQuery("SELECT s FROM Student s WHERE s.email = :email")
            .setParameter("email", email).getSingleResult(); */
//typed query
