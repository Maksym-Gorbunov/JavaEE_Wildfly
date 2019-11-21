package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.model.TeacherModel;
import se.alten.schoolproject.model.SubjectModel;

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

  private static final Logger LOGGER = (Logger) Logger.getLogger(TeacherController.class.getName());

  @Inject
  private SchoolAccessLocal sal;


  @GET
  @Produces({"application/JSON"})
  public Response getTeachers() {
    LOGGER.info("Controller: getTeachers()");
    try {
      List<TeacherModel> result = sal.getTeachers();
      return Response.ok(result).build();
    } catch (Exception e) {
      return Response.status(Response.Status.CONFLICT).build();
    }
  }


  @POST
  @Path("/add")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({"application/JSON"})
  public Response addTeacher(String teacherBody) {
    LOGGER.info("Controller: addTeacher()");
    try {
      TeacherModel result = sal.addTeacher(teacherBody);
      if (result.getForename().equals("empty")) {
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build(); //406
      }
      return Response.ok(result).build();
    } catch (EJBTransactionRolledbackException | PersistenceException e) {
      LOGGER.info("addTeacher: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Teacher already exist!\"}").build(); //417
    } catch (RuntimeException e) {
      LOGGER.info("addTeacher: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build(); //404
    } catch (Exception e) {
      LOGGER.info("addTeacher: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build(); //400
    }
  }


  @DELETE
  @Produces({"application/JSON"})
  @Path("/delete/{email}")
  public Response deleteTeacher(@PathParam("email") String email) {
    LOGGER.info("Controller: deleteTeacher()");
    try {
      String result = sal.deleteTeacher(email);
      if (result.equals("empty")) {
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Can't delete teacher with empty email!\"}").build(); //406
      }
      if (result.equals("")) {
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Teacher not found!\"}").build(); //406
      }
      return Response.ok().entity("{\"Teacher \"" + result + "\" was deleted from database!\"}").build();
    } catch (NoResultException e) {
      LOGGER.info("deleteTeacher: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Teacher with current email not found!\"}").build(); //417
    } catch (EJBTransactionRolledbackException | PersistenceException e) {
      LOGGER.info("deleteTeacher: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Teacher not found!\"}").build(); //417
    } catch (RuntimeException e) {
      LOGGER.info("deleteTeacher: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build(); //404
    } catch (Exception e) {
      LOGGER.info("deleteTeacher: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build(); //400
    }
  }


  @GET
  @Path("find/email/{email}")
  @Produces({"application/JSON"})
  public Response findTeacherByEmail(@PathParam("email") String email) {
    try {
      LOGGER.info("Controller: findTeacherByEmail()");
      TeacherModel result = sal.findTeacherByEmail(email);
      return Response.ok(result).build();
    } catch (EJBTransactionRolledbackException | PersistenceException e) {
      LOGGER.info("findTeacherByEmail: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Teacher with current email not found!\"}").build(); //417
    } catch (RuntimeException e) {
      LOGGER.info("findTeacherByEmail: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build(); //404
    } catch (Exception e) {
      LOGGER.info("findTeacherByEmail: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build(); //400
    }
  }


  @GET
  @Path("find/forename/{forename}")
  @Produces({"application/JSON"})
  public Response findAllTeachersByForename(@PathParam("forename") String forename) {
    try {
      LOGGER.info("Controller: findTeachersByForename");
      List<TeacherModel> result = sal.findTeachersByForename(forename);
      return Response.ok(result).build();
    } catch (EJBTransactionRolledbackException | PersistenceException e) {
      LOGGER.info("findTeachersByForename: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Teacher with current email not found!\"}").build(); //417
    } catch (RuntimeException e) {
      LOGGER.info("findTeachersByForename: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build(); //404
    } catch (Exception e) {
      LOGGER.info("findTeachersByForename: " + e.getClass().getSimpleName());
      return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build(); //400
    }
  }

}







