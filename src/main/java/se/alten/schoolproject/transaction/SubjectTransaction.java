package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.rest.StudentController;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Default
public class SubjectTransaction implements SubjectTransactionAccess {

  private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());

  @PersistenceContext(unitName = "school")
  private EntityManager em;

  @Override
  public List listAllSubjects() {
    System.out.println("listAllSubjects() - Transaction");
//        LOGGER.info("listAllSubjects() - Transaction");
    Query query = em.createQuery("SELECT s FROM Subject s");
    return query.getResultList();
  }

  @Override
  public Subject addSubject(Subject subject) {
    em.persist(subject);
    em.flush();
    return subject;
//        try {
//            em.persist(subject);
//            em.flush();
//            return subject;
//        } catch ( PersistenceException pe ) {
//            subject.setTitle("duplicate");
//            return subject;
//        }
  }

  @Override
  public List<Subject> getSubjectByName(List<String> subject) {

    String queryStr = "SELECT sub FROM Subject sub WHERE sub.title IN :subject";
    TypedQuery<Subject> query = em.createQuery(queryStr, Subject.class);
    query.setParameter("subject", subject);

    return query.getResultList();
  }

  @Override
  public String deleteSubject(String title) {
    Query query = em.createQuery("DELETE FROM Subject s WHERE s.title = :title");
    query.setParameter("title", title);
    int rowsDeleted = query.executeUpdate();
    if(rowsDeleted < 1){
      throw new NoResultException();
    }
    return title;
  }
}
