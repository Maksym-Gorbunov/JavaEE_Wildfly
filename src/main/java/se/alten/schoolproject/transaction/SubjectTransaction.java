package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Subject;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Default
public class SubjectTransaction implements SubjectTransactionAccess {

  private static final Logger LOGGER = (Logger) Logger.getLogger(SubjectTransaction.class.getName());

  @PersistenceContext(unitName = "school")
  private EntityManager em;


  @Override
  public List getSubjects() {
    System.out.println("getSubjects() - Transaction");
    Query query = em.createQuery("SELECT s FROM Subject s");
    return query.getResultList();
  }


  @Override
  public Subject addSubject(Subject subject) {
    em.persist(subject);
    em.flush();
    return subject;
  }


  @Override
  public String deleteSubject(String title) {
    Query query = em.createQuery("DELETE FROM Subject s WHERE s.title = :title");
    query.setParameter("title", title);
    int rowsDeleted = query.executeUpdate();
    if (rowsDeleted < 1) {
      return "";
    }
    return title;
  }

  @Override
  public Subject getSubjectByTitle(String subjectTitle) {
    Subject foundedSubject = em.createQuery("SELECT s FROM Subject s WHERE s.title = :title", Subject.class)
            .setParameter("title", subjectTitle).getSingleResult();
    return foundedSubject;
  }

  @Override
  public Subject updateSubject(String title, String newTitle) {
    String queryStr = "UPDATE Subject SET title = :newTitle WHERE title = :title";
    TypedQuery<Subject> query = em.createQuery(queryStr, Subject.class);
    query.setParameter("newTitle", newTitle)
            .setParameter("title", title)
            .executeUpdate();
    return query.getSingleResult();
  }


  @Override
  public Subject findSubjectByTitle(String title) {
    String queryStr = "SELECT s FROM Subject s WHERE s.title = :title";
    TypedQuery<Subject> query = em.createQuery(queryStr, Subject.class);
    query.setParameter("title", title);
    return query.getSingleResult();
  }


  @Override
  public List<Subject> findAllSubjectsByTitleList(List<String> titleList) {
    String queryStr = "SELECT sub FROM Subject sub WHERE sub.title IN :titleList";
    TypedQuery<Subject> query = em.createQuery(queryStr, Subject.class);
    query.setParameter("titleList", titleList);
    return query.getResultList();
  }

}
