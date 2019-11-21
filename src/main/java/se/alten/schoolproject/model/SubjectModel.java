package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Subject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectModel {

    private Long id;
    private String title;

    private Set<String> students = new HashSet<>();

    private static final Logger LOGGER = (Logger) Logger.getLogger(SubjectModel.class.getName());


    public SubjectModel(String title) {
        this.title = title;
    }


    public SubjectModel toModel(Subject subjectToAdd) {
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setTitle(subjectToAdd.getTitle());

        subjectToAdd.getStudent().forEach(stud -> {
            subjectModel.students.add(stud.getEmail());
        });

        return subjectModel;
    }


    public List<SubjectModel> toModelList(List<Subject> subjects) {
        List<SubjectModel> subjectModels = new ArrayList<>();
        subjects.forEach(subj -> {
            SubjectModel sm = toModel(subj);

            subj.getStudent().forEach(stud -> {
                sm.students.add(stud.getEmail());
            });

            subjectModels.add(sm);
        });





        return subjectModels;
    }
}
