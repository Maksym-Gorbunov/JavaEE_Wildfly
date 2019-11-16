package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@NoArgsConstructor
@Path("/subject")
public class SubjectController {

  private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());

  @Inject
  private SchoolAccessLocal sal;

  @GET
  @Produces({"application/JSON"})
  public Response listSubjects() {
    System.out.println("listAllSubjects() - Controller");
//        LOGGER.info("listAllSubjects() - Controller");
    try {
      List subject = sal.listAllSubjects();
      return Response.ok(subject).build();
    } catch (Exception e) {
      return Response.status(Response.Status.CONFLICT).build();
    }
  }

  @POST
  @Path("/add")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({"application/JSON"})
  public Response addSubject(String subjectBody) {
    try {
      System.out.println("--- Controller: addSubject() ---");
      SubjectModel subjectModel = sal.addSubject(subjectBody);
      if (subjectModel.getTitle().equals("empty")) {
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build(); //406
      }
      return Response.ok(subjectModel).build();
    } catch (EJBTransactionRolledbackException | PersistenceException e) {
      System.out.println("add: " + e.toString());
      return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Subject already exist!\"}").build(); //417
    } catch (RuntimeException e) {
      System.out.println("add: " + e.toString());
      return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build(); //404
    } catch (Exception e) {
      System.out.println("add: " + e.toString());
      return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build(); //400
    }
  }

  /*
  @POST
  @Produces({"application/JSON"})
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addSubject(String subject) {
    try {
      SubjectModel subjectModel = sal.addSubject(subject);
      return Response.ok(subjectModel).build();
    } catch (Exception e) {
      return Response.status(404).build();
    }
  }
  */

}
