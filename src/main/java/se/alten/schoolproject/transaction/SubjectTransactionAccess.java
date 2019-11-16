package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SubjectTransactionAccess {
  List listAllSubjects();

  Subject addSubject(Subject subject);

  List<Subject> getSubjectByName(List<String> subject);

  String deleteSubject(String title);
}
