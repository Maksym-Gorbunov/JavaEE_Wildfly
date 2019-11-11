package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.rest.StudentController;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Default

//JPQL Query

public class StudentTransaction implements StudentTransactionAccess{

    private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Override
    public List listAllStudents() {
        Query query = entityManager.createQuery("SELECT s from Student s");
        return query.getResultList();
    }


    @Override
    public Student addStudent(Student studentToAdd) {
        try {
            entityManager.persist(studentToAdd);
            entityManager.flush();
            return studentToAdd;
        } catch ( PersistenceException pe ) {
            studentToAdd.setForename("duplicate");
            return studentToAdd;
        }
    }


    @Override
    public Student removeStudent(String email) {
      Student studentToRemove = new Student();
      Student removedStudent = new Student();

      studentToRemove = (Student)entityManager.createQuery("SELECT s FROM Student s WHERE s.email = :email")
              .setParameter("email", email).getSingleResult();

      removedStudent.setId(studentToRemove.getId());
      removedStudent.setForename(studentToRemove.getForename());
      removedStudent.setLastname(studentToRemove.getLastname());
      removedStudent.setEmail(studentToRemove.getEmail());

      Query query = entityManager.createQuery("DELETE FROM Student s WHERE s.email = :email");
      query.setParameter("email", email)
              .executeUpdate();
      return removedStudent;
    }


    @Override
    public void updateStudent(String forename, String lastname, String email) {
        Query updateQuery = entityManager.createNativeQuery("UPDATE student SET forename = :forename, lastname = :lastname WHERE email = :email", Student.class);
        updateQuery.setParameter("forename", forename)
                   .setParameter("lastname", lastname)
                   .setParameter("email", email)
                   .executeUpdate();
    }

    @Override
    public void updateStudentPartial(Student student) {
        Student studentFound = (Student)entityManager.createQuery("SELECT s FROM Student s WHERE s.email = :email")
                .setParameter("email", student.getEmail()).getSingleResult();

        Query query = entityManager.createQuery("UPDATE Student SET forename = :studentForename WHERE email = :email");
        query.setParameter("studentForename", student.getForename())
                .setParameter("email", studentFound.getEmail())
                .executeUpdate();
    }


    @Override
    public List<Student> findStudentByName(String forename) {
        List<Student> foundedStudents = entityManager.createQuery("SELECT s FROM Student s WHERE s.forename = :forename")
                .setParameter("forename", forename).getResultList();
        return foundedStudents;
    }
}
