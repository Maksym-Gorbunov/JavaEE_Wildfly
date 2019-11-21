package se.alten.schoolproject.dao;

import se.alten.schoolproject.model.SubjectModel;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SchoolAccessLocal {



  List getSubjects();

  SubjectModel addSubject(String subjectModel);

  String deleteSubject(String title);


  SubjectModel findSubjectByTitle(String title);

  List<SubjectModel> findAllSubjectsByTitleList(String titleList);
}
