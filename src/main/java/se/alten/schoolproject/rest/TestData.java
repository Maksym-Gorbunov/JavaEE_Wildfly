package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


/*
* Populate database med test data
* ex: http://localhost:8080/javaEnterprise/fill
* */

@Stateless
@NoArgsConstructor
@Path("/fill")
public class TestData {

  @Inject
  private SchoolAccessLocal sal;

  @GET
  @Produces({"application/JSON"})
  public Response fill() {
    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<< fill() >>>>>>>>>>>>>>>>>>>>>>>>>>");
    populateSubjects();
    populateStudents();
    return Response.ok().entity("{\"Test data was added to database!\"}").build();
  }

  private void populateStudents(){
    List<String> students = new ArrayList<>();
    students.add("{\n" +
            "    \"forename\": \"Max\",\n" +
            "    \"lastname\": \"Fry\",\n" +
            "    \"email\": \"max.fry@com\",\n" +
            "    \"subjects\": [\n" +
            "        \"AI\",\n" +
            "        \"ML\"\n" +
            "    ]\n" +
            "}");
    students.add("{\n" +
            "    \"forename\": \"Tom\",\n" +
            "    \"lastname\": \"Hanks\",\n" +
            "    \"email\": \"tom.hanks@com\",\n" +
            "    \"subjects\": [\n" +
            "        \"Javascript\",\n" +
            "        \"ML\"\n" +
            "    ]\n" +
            "}");
    students.add("{\n" +
            "    \"forename\": \"Andy\",\n" +
            "    \"lastname\": \"Flatcher\",\n" +
            "    \"email\": \"andy.flatcher@com\",\n" +
            "    \"subjects\": [\n" +
            "        \"Python\",\n" +
            "        \"AI\"\n" +
            "    ]\n" +
            "}");
    students.add("{\n" +
            "    \"forename\": \"Anna\",\n" +
            "    \"lastname\": \"Lee\",\n" +
            "    \"email\": \"anna.lee@com\",\n" +
            "    \"subjects\": [\n" +
            "        \"Java\",\n" +
            "        \"Javascript\"\n" +
            "    ]\n" +
            "}");
    students.add("{\n" +
            "    \"forename\": \"Nils\",\n" +
            "    \"lastname\": \"Karlsson\",\n" +
            "    \"email\": \"nils.karlsson@com\",\n" +
            "    \"subjects\": [\n" +
            "        \"OOP\",\n" +
            "        \"Programming\"\n" +
            "    ]\n" +
            "}");
    students.add("{\n" +
            "    \"forename\": \"Robert\",\n" +
            "    \"lastname\": \"Larssson\",\n" +
            "    \"email\": \"robert.larsson@com\",\n" +
            "    \"subjects\": [\n" +
            "        \"Ruby\",\n" +
            "        \"Java\"\n" +
            "    ]\n" +
            "}");
    students.stream().forEach(s -> sal.addStudent(s));
  }

  private void populateSubjects(){
    List<String> subjects = new ArrayList<>();
    subjects.add("{\"title\":\"Javascript\"}");
    subjects.add("{\"title\":\"Python\"}");
    subjects.add("{\"title\":\"Ruby\"}");
    subjects.add("{\"title\":\"ML\"}");
    subjects.add("{\"title\":\"AI\"}");
    subjects.add("{\"title\":\"Programming\"}");
    subjects.add("{\"title\":\"OOP\"}");
    subjects.stream().forEach(s -> sal.addSubject(s));
  }
}
