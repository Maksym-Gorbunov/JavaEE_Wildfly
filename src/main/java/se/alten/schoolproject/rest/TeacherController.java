package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.TeacherModel;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@NoArgsConstructor
@Path("/teacher")
public class TeacherController {

  private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());

  @Inject
  private SchoolAccessLocal sal;


  @GET
  @Produces({"application/JSON"})
  public Response listAllTeachers() {
    System.out.println("listAllTeachers() - Controller");
    try {
      List students = sal.listAllTeachers();
      return Response.ok(students).build();
    } catch (Exception e) {
      return Response.status(Response.Status.CONFLICT).build();
    }
  }


  @POST
  @Path("/add")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({"application/JSON"})
  public Response addTeacher(String teacherBody) {
    try {
      System.out.println("addTeacher() - Controller");
      TeacherModel teacherModel = sal.addTeacher(teacherBody);
      if (teacherModel.getForename().equals("empty")) {
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build(); //406
      }
      return Response.ok(teacherModel).build();
    } catch (EJBTransactionRolledbackException | PersistenceException e) {
      System.out.println("add: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Teacher already exist!\"}").build(); //417
    } catch (RuntimeException e) {
      System.out.println("add: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build(); //404
    } catch (Exception e) {
      System.out.println("add: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build(); //400
    }
  }


  @DELETE /////////()
  @Produces({"application/JSON"})
  @Path("/delete/{email}")
  public Response deleteTeacher(@PathParam("email") String email) {
    System.out.println("--- Controller: deleteTeacher() ---");
    try {
      String answer = sal.deleteTeacher(email);
      if (answer.equals("empty")) {
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Can't delete teacher with empty email!\"}").build(); //406
      }
      return Response.ok().entity("{\"Teacher \"" + answer + "\" was deleted from database!\"}").build();
    } catch (NoResultException e) {
      System.out.println("delete: " + e.toString());
      return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Teacher with current email not found!\"}").build(); //417
    } catch (EJBTransactionRolledbackException | PersistenceException e) {
      System.out.println("delete: " + e.toString());
      return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Teacher not found!\"}").build(); //417
    } catch (RuntimeException e) {
      System.out.println("delete: " + e.toString());
      return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build(); //404
    } catch (Exception e) {
      System.out.println("delete: " + e.toString());
      return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build(); //400
    }
  }


}
