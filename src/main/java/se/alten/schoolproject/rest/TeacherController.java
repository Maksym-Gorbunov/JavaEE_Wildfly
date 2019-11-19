package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.TeacherModel;

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

      //if (studentModel.getForename().equals("empty")) {
       // return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build(); //406
      //}
      return Response.ok(teacherModel).build();
    } catch (
            EJBTransactionRolledbackException | PersistenceException e) {
      System.out.println("add: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Teacher already exist!\"}").build(); //417
    } catch (
            RuntimeException e) {
      System.out.println("add: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build(); //404
    } catch (
            Exception e) {
      System.out.println("add: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build(); //400
    }

  }


}
