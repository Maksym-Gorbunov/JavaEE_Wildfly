package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherTransaction implements TeacherTransactionAccess {

  //private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());

  @PersistenceContext(unitName = "school")
  private EntityManager em;


  @Override
  public List getTeachers() {
    System.out.println("getTeachers() - Transaction");
    Query query = em.createQuery("SELECT t FROM Teacher t");
    List<Student> temp = query.getResultList();
//    List<Student> result = temp.stream()
//            .distinct()
//            .collect(Collectors.toList());
//    return result;
    return temp;
  }


  @Override
  public Teacher addTeacher(Teacher teacher) {
    em.persist(teacher);
    em.flush();
    return teacher;
  }


  @Override
  public String deleteTeacher(String email) {
    Query query = em.createQuery("DELETE FROM Teacher t WHERE t.email = :email");
    query.setParameter("email", email);
    int rowsDeleted = query.executeUpdate();
    if (rowsDeleted < 1) {
      return "";
    }
    return email;
  }


  @Override
  public Teacher updateTeacher(String forename, String lastname, String email) {
    Teacher teacher = (Teacher) em
            .createQuery("SELECT t FROM Teacher t WHERE t.email = :email")
            .setParameter("email", email).getSingleResult();
    Query query = em.createQuery("UPDATE Teacher SET forename = :forename, lastname = :lastname WHERE email = :email");
    query.setParameter("forename", forename)
            .setParameter("lastname", lastname)
            .setParameter("email", teacher.getEmail())
            .executeUpdate();
    return teacher;

  }


  @Override
  public Teacher updateTeacherPartial(Teacher teacher) {
    Teacher teacherToUpdate = (Teacher) em
            .createQuery("SELECT t FROM Teacher t WHERE t.email = :email")
            .setParameter("email", teacher.getEmail()).getSingleResult();
    Query query = em.createQuery("UPDATE Student SET forename = :forename, lastname = :lastname WHERE email = :email");
    query.setParameter("forename", teacher.getForename())
            .setParameter("lastname", teacher.getLastname())
            .setParameter("email", teacherToUpdate.getEmail())
            .executeUpdate();
    return teacherToUpdate;

  }


  @Override
  public List<Teacher> findTeachersByForename(String forename) {
    List<Teacher> foundedTeachers = em.createQuery("SELECT t FROM Teacher t WHERE t.forename = :forename")
            .setParameter("forename", forename).getResultList();
    return foundedTeachers;

  }


  @Override
  public Teacher findTeacherByEmail(String email) {
    Teacher foundedTeacher = em.createQuery("SELECT t FROM Teacher t WHERE t.email = :email", Teacher.class)
            .setParameter("email", email).getSingleResult();
    return foundedTeacher;
  }

}
