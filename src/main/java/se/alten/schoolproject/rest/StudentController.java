package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.model.StudentModel;
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
@Path("/student")
public class StudentController {

    private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());

    @Inject
    private SchoolAccessLocal sal;


    @GET
    @Produces({"application/JSON"})
    public Response getStudents() {
        LOGGER.info("Controller: getTransientStudents()");
        try {
            List<StudentModel> result = sal.getStudents();
            return Response.ok(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }


    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/JSON"})
    public Response addStudent(String studentBody) {
        LOGGER.info("Controller: addStudent()");
        try {
            StudentModel result = sal.addStudent(studentBody);
            if (result.getForename().equals("empty")) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build(); //406
            }
            return Response.ok(result).build();
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            LOGGER.info("addStudent: " + e.getClass().getSimpleName());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Student already exist!\"}").build(); //417
        } catch (RuntimeException e) {
            LOGGER.info("addStudent: " + e.getClass().getSimpleName());
            return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build(); //404
        } catch (Exception e) {
            LOGGER.info("addStudent: " + e.getClass().getSimpleName());
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build(); //400
        }
    }


    @DELETE
    @Produces({"application/JSON"})
    @Path("/delete/{email}")
    public Response deleteStudent(@PathParam("email") String email) {
        LOGGER.info("Controller: deleteStudent()");
        try {
            String result = sal.deleteStudent(email);
            if (result.equals("empty")) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Can't delete student with empty email!\"}").build(); //406
            }
            if (result.equals("")) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Student not found!\"}").build(); //406
            }
            return Response.ok().entity("{\"Student \"" + result + "\" was deleted from database!\"}").build();
        } catch (NoResultException e) {
            LOGGER.info("deleteStudent: " + e.getClass().getSimpleName());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Student with current email not found!\"}").build(); //417
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            LOGGER.info("deleteStudent: " + e.getClass().getSimpleName());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Student not found!\"}").build(); //417
        } catch (RuntimeException e) {
            LOGGER.info("deleteStudent: " + e.getClass().getSimpleName());
            return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build(); //404
        } catch (Exception e) {
            LOGGER.info("deleteStudent: " + e.getClass().getSimpleName());
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build(); //400
        }
    }


    @GET
    @Path("find/email/{email}")
    @Produces({"application/JSON"})
    public Response findStudentByEmail(@PathParam("email") String email) {
        try {
            LOGGER.info("Controller: findStudentByEmail()");
            StudentModel result = sal.findStudentByEmail(email);
            return Response.ok(result).build();
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            LOGGER.info("findStudentByEmail: " + e.getClass().getSimpleName());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Student with current email not found!\"}").build(); //417
        } catch (RuntimeException e) {
            LOGGER.info("findStudentByEmail: " + e.getClass().getSimpleName());
            return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build(); //404
        } catch (Exception e) {
            LOGGER.info("findStudentByEmail: " + e.getClass().getSimpleName());
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build(); //400
        }
    }


    @GET
    @Path("find/forename/{forename}")
    @Produces({"application/JSON"})
    public Response findAllStudentsByForename(@PathParam("forename") String forename) {
        try {
            LOGGER.info("Controller: findStudentsByForename");
            List<StudentModel> result = sal.findStudentsByForename(forename);
            return Response.ok(result).build();
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            LOGGER.info("findStudentsByForename: " + e.getClass().getSimpleName());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Student with current email not found!\"}").build(); //417
        } catch (RuntimeException e) {
            LOGGER.info("findStudentsByForename: " + e.getClass().getSimpleName());
            return Response.status(Response.Status.NOT_FOUND).entity("{\"Could not find resource for full path!\"}").build(); //404
        } catch (Exception e) {
            LOGGER.info("findStudentsByForename: " + e.getClass().getSimpleName());
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Oops. Server side error!\"}").build(); //400
        }
    }

}







