package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

  private static final Logger LOGGER = (Logger) Logger.getLogger(SchoolDataAccess.class.getName());

  private Subject subject = new Subject();
  private SubjectModel subjectModel = new SubjectModel();
  @Inject
  SubjectTransactionAccess subjectTA;


  //////////////////////////////////// Subject //////////////////////////////////////////

  @Override
  public List<SubjectModel> listAllSubjects() {
    LOGGER.info("SDA: listAllSubjects()");
    List<Subject> dbResponse = subjectTA.listAllSubjects();
    return subjectModel.toModelList(dbResponse);
  }


  @Override
  public SubjectModel addSubject(String subjectBody) {
    LOGGER.info("SDA: addSubject()");
    if (subjectBody.isBlank()) {
      return new SubjectModel("empty");
    }
    Subject subj = subject.toEntity(subjectBody);
    Subject dbResponse = subjectTA.addSubject(subj);
    return this.subjectModel.toModel(dbResponse);
  }


  @Override
  public String deleteSubject(String title) {
    LOGGER.info("SDA: deleteSubject()");
    if ((title == null) || (title.isBlank())) {
      return "empty";
    }
    return subjectTA.deleteSubject(title);
  }




}
