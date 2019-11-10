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

    //private static final Logger LOGGER = (Logger) Logger.getLogger(StudentController.class.getName());

    public StudentModel toModel(Student student) {
        //LOGGER.info(222+student.toString());
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
