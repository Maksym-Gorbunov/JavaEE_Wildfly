package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class TeacherTransaction  implements TeacherTransactionAccess{

  @PersistenceContext(unitName = "school")
  private EntityManager em;


  @Override
  public List listAllTeachers() {
    System.out.println("listAllTeachers() - Transaction");
    //Query query = em.createQuery("SELECT t FROM Teacher t");
    Query query = em.createQuery("SELECT t FROM Teacher t JOIN FETCH t.student");
    return query.getResultList();
  }


  @Override
  public Teacher addTeacher(Teacher teacherToAdd) {
    em.persist(teacherToAdd);
    em.flush();
    return teacherToAdd;
  }
}
