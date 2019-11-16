package se.alten.schoolproject.transaction;

import org.hibernate.Session;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.rest.StudentController;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Stateless
@Default
public class SubjectTransaction implements SubjectTransactionAccess{

    private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Override
    public List listAllSubjects() {
        System.out.println("listAllSubjects() - Transaction");
//        LOGGER.info("listAllSubjects() - Transaction");
        Query query = entityManager.createQuery("SELECT s FROM Subject s");
        return query.getResultList();
    }

    @Override
    public Subject addSubject(Subject subject) {
        try {
            entityManager.persist(subject);
            entityManager.flush();
            return subject;
        } catch ( PersistenceException pe ) {
            subject.setTitle("duplicate");
            return subject;
        }
    }

    @Override
    public List<Subject> getSubjectByName(List<String> subject) {

        String queryStr = "SELECT sub FROM Subject sub WHERE sub.title IN :subject";
        TypedQuery<Subject> query = entityManager.createQuery(queryStr, Subject.class);
        query.setParameter("subject", subject);

        return query.getResultList();
     }
}
