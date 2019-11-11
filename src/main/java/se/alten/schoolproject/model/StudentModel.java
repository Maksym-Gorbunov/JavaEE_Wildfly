package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.rest.StudentController;

import java.util.logging.Logger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentModel {

    private Long id;
    private String forename;
    private String lastname;
    private String email;
    //private String status;

    //private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());


//    public StudentModel(String forename, String lastname, String email) {
//        this.forename = forename;
//        this.lastname = lastname;
//        this.email = email;
//    }

    public StudentModel toModel(Student student) {
        //StudentModel studentModel = new StudentModel(student.getForename(), student.getLastname(),student.getEmail());
        StudentModel studentModel = new StudentModel();
        switch (student.getForename()) {
            case "empty":
                studentModel.setForename("empty");
                return studentModel;
            case "duplicate":
                studentModel.setForename("duplicate");
                return studentModel;
            default:
                studentModel.setForename(student.getForename());
                studentModel.setLastname(student.getLastname());
                studentModel.setEmail(student.getEmail());
                return studentModel;
        }
    }
}
