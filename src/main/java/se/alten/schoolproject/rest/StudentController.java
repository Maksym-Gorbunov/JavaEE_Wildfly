package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.model.StudentModel;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Console;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@NoArgsConstructor
@Path("/student")
public class StudentController {

  private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());

  @Inject
  private SchoolAccessLocal sal;

  //return all students
  @GET
  @Produces({"application/JSON"})
  public Response showStudents() {
    try {
      List students = sal.listAllStudents();
      return Response.ok(students).build();
    } catch (Exception e) {
      return Response.status(Response.Status.CONFLICT).build();
    }
  }


  @POST
  @Path("/add")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({"application/JSON"})
  public Response addStudent(String studentBody) {
    try {
      LOGGER.info("---delete---");
      StudentModel studentModel = sal.addStudent(studentBody);

      if (studentModel.getForename().equals("empty")) {
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build();  //406
      }
      return Response.ok(studentModel).build();
    } catch (
            EJBTransactionRolledbackException e) {
      LOGGER.info("add: " + e.toString());
      return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Student already exist!\"}").build();//417
    } catch (
            RuntimeException e) {
      LOGGER.info("add: " + e.toString());
      return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build();    //404
    } catch (
            Exception e) {
      LOGGER.info("add: " + e.toString());
      return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build();    //400
    }

  }


  @DELETE
  @Produces({"application/JSON"})
  @Path("/delete/{email}")
  public Response deleteUser(@PathParam("email") String email) {
    try {
      LOGGER.info("---delete---");
      StudentModel studentModel = sal.removeStudent(email);
      return Response.ok(studentModel).build();
    } catch (EJBTransactionRolledbackException e) {
      LOGGER.info("delete: " + e.toString());
      return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Student with current email not found!\"}").build();//417
    } catch (RuntimeException e) {
      LOGGER.info("delete: " + e.toString());
      return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build();    //404
    } catch (Exception e) {
      LOGGER.info("delete: " + e.toString());
      return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build();    //400
    }
  }


  //toDo  change to StudentModel
  //toDo  change to StudentModel
  //toDo  change to StudentModel
  @PUT
  @Path("/update")
  public void updateStudent(@QueryParam("forename") String forename, @QueryParam("lastname") String lastname, @QueryParam("email") String email) {
    sal.updateStudent(forename, lastname, email);
  }

  @PATCH
  public Response updateStudentPartial(String studentBody) {
    try {
      LOGGER.info("---updateStudentPartial---");
      StudentModel studentModel = sal.updateStudentPartial(studentBody);
      if (studentModel.getForename().equals("empty")) {
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build();  //406
      }
      return Response.ok(studentModel).build();
    } catch (EJBTransactionRolledbackException e) {
      LOGGER.info("updateStudentPartial: " + e.toString());
      return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Student with current email not found!\"}").build();//417
    } catch (RuntimeException e) {
      LOGGER.info("updateStudentPartial: " + e.toString());
      return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build();    //404
    } catch (Exception e) {
      LOGGER.info("updateStudentPartial: " + e.toString());
      return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build();    //400
    }
  }

  @GET
  @Path("find/{name}")
  @Produces({"application/JSON"})
  public Response findStudentByName(@PathParam("name") String name) {
    try {
      LOGGER.info("---findByName---");
      List<StudentModel> studentModels = sal.findStudentByName(name);
      return Response.ok(studentModels).build();
    } catch (EJBTransactionRolledbackException e) {
      LOGGER.info("findByName: " + e.toString());
      return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Student with current email not found!\"}").build();//417
    } catch (RuntimeException e) {
      LOGGER.info("findByName: " + e.toString());
      return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build();    //404
    } catch (Exception e) {
      LOGGER.info("findByName: " + e.toString());
      return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build();    //400
    }
  }
}



