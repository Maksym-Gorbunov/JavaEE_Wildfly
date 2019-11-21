package se.alten.schoolproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;


    private static final Logger LOGGER = (Logger) Logger.getLogger(Subject.class.getName());


    public Subject toEntity(String subjectModel) {
        JsonReader reader = Json.createReader(new StringReader(subjectModel));
        JsonObject jsonObject = reader.readObject();
        Subject subject = new Subject();
        if ( jsonObject.containsKey("subject")) {
            subject.setTitle(jsonObject.getString("subject"));
        } else {
            subject.setTitle("");
        }
        return subject;
    }

}
