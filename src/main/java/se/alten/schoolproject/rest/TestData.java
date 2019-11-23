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
* Populate database with fake data
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
    populateTeachers();
    populateStudents();
    populateSubjects();
    return Response.ok().entity("{\"Test data was added to database!\"}").build();
  }


  private void populateTeachers(){
    List<String> teachers = new ArrayList<>();
    teachers.add("{\n" +
            "        \"forename\": \"Philip\",\n" +
            "        \"lastname\": \"Axelsson\",\n" +
            "        \"email\": \"philip.axelsson@iths.se\"\n" +
            "    }");
    teachers.add("{\n" +
            "        \"forename\": \"Anton\",\n" +
            "        \"lastname\": \"Fry\",\n" +
            "        \"email\": \"anton.fry@iths.se\"\n" +
            "    }");
    teachers.add("{\n" +
            "        \"forename\": \"Niklas\",\n" +
            "        \"lastname\": \"Storm\",\n" +
            "        \"email\": \"niklas.storm@iths.se\"\n" +
            "    }");
    teachers.add("{\n" +
            "        \"forename\": \"Erik\",\n" +
            "        \"lastname\": \"Karsson\",\n" +
            "        \"email\": \"erik.karlsson@iths.se\"\n" +
            "    }");
    teachers.add("{\n" +
            "        \"forename\": \"Patrik\",\n" +
            "        \"lastname\": \"Norman\",\n" +
            "        \"email\": \"patrik.norman@iths.se\"\n" +
            "    }");
    teachers.add("{\n" +
            "        \"forename\": \"Kalle\",\n" +
            "        \"lastname\": \"Larsson\",\n" +
            "        \"email\": \"kalle.larsson@iths.se\"\n" +
            "    }");
    teachers.add("{\n" +
            "        \"forename\": \"Pontus\",\n" +
            "        \"lastname\": \"Sandqvist\",\n" +
            "        \"email\": \"pontus.sandqvist@iths.se\"\n" +
            "    }");
    teachers.stream().forEach(t -> sal.addTeacher(t));
  }


  private void populateStudents(){
    List<String> students = new ArrayList<>();
    students.add("{\n" +
            "        \"forename\": \"Kim\",\n" +
            "        \"lastname\": \"Axelsson\",\n" +
            "        \"email\": \"kim.axelsson@mail.com\"\n" +
            "    }");
    students.add("{\n" +
            "        \"forename\": \"Max\",\n" +
            "        \"lastname\": \"Fry\",\n" +
            "        \"email\": \"max.fry@mail.com\"\n" +
            "    }");
    students.add("{\n" +
            "        \"forename\": \"Bob\",\n" +
            "        \"lastname\": \"Storm\",\n" +
            "        \"email\": \"bob.storm@mail.com\"\n" +
            "    }");
    students.add("{\n" +
            "        \"forename\": \"Nils\",\n" +
            "        \"lastname\": \"Karsson\",\n" +
            "        \"email\": \"nils.karlsson@mail.com\"\n" +
            "    }");
    students.add("{\n" +
            "        \"forename\": \"Alex\",\n" +
            "        \"lastname\": \"Norman\",\n" +
            "        \"email\": \"alex.norman@mail.com\"\n" +
            "    }");
    students.add("{\n" +
            "        \"forename\": \"Robert\",\n" +
            "        \"lastname\": \"Larsson\",\n" +
            "        \"email\": \"robert.larsson@mail.com\"\n" +
            "    }");
    students.add("{\n" +
            "        \"forename\": \"Lena\",\n" +
            "        \"lastname\": \"Sandqvist\",\n" +
            "        \"email\": \"lena.sandqvist@mail.com\"\n" +
            "    }");
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
