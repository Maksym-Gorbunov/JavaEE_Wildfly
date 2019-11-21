package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

  private static final Logger LOGGER = (Logger) Logger.getLogger(SchoolDataAccess.class.getName());

  private Subject subject = new Subject();
  private SubjectModel subjectModel = new SubjectModel();
  @Inject
  SubjectTransactionAccess subjectTA;


  //////////////////////////////////// Subject start //////////////////////////////////////////

  @Override
  public List<SubjectModel> getSubjects() {
    LOGGER.info("SDA: getSubjects()");
    List<Subject> dbResponse = subjectTA.getSubjects();
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


  @Override
  public SubjectModel findSubjectByTitle(String title) {
    if ((title == null) || (title.isEmpty())) {
      return null;
    }
    Subject dbResponse = subjectTA.findSubjectByTitle(title);
    return subjectModel.toModel(dbResponse);
  }


  @Override
  public List<SubjectModel> findAllSubjectsByTitleList(String titleListBody) {
    if ((titleListBody == null) || (titleListBody.isEmpty())) {
      return null;
    }

    List<String> titleList = new ArrayList<>();

    JsonReader reader = Json.createReader(new StringReader(titleListBody));
    JsonObject jsonObject = reader.readObject();

    if (jsonObject.containsKey("subjects")) {
      JsonArray jsonArray = jsonObject.getJsonArray("subjects");
      for(JsonValue title : jsonArray){
        titleList.add(title.toString().replace("\"", ""));
      }
    }

    if (titleList.size() > 0) {
      List<SubjectModel> models = new ArrayList<>();
      List<Subject> dbResponse = subjectTA.findAllSubjectsByTitleList(titleList);
      for (Subject subj : dbResponse) {
        models.add(subjectModel.toModel(subj));
      }
      return models;
    }
    return null;
  }

  //////////////////////////////////// Subject end //////////////////////////////////////////


}
