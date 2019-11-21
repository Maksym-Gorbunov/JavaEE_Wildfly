package se.alten.schoolproject.dao;

import se.alten.schoolproject.model.SubjectModel;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SchoolAccessLocal {



  List listAllSubjects();

  SubjectModel addSubject(String subjectModel);

  String deleteSubject(String title);


}
