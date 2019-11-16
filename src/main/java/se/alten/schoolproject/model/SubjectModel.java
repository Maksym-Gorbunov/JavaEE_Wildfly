package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectModel {

    private Long id;
    private String title;

    public SubjectModel(String title) {
        this.title = title;
    }

    public SubjectModel toModel(Subject subjectToAdd) {
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setTitle(subjectToAdd.getTitle());
        return subjectModel;
    }


    public List<SubjectModel> toModelList(List<Subject> subjects) {
        List<SubjectModel> subjectModels = new ArrayList<>();

        subjects.forEach(subject -> {
            SubjectModel sm = new SubjectModel();
            sm = toModel(subject);
            subjectModels.add(sm);
        });
        return subjectModels;
    }
}
