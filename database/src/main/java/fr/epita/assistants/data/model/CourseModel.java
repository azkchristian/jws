package fr.epita.assistants.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name="course_model")
@Entity
@NoArgsConstructor
@Setter
@Getter
public class CourseModel
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name", length = 255)
    public String name;

    @Column(name = "tag", length = 255)
    @ElementCollection
    @CollectionTable(name="course_model_tags", joinColumns = @JoinColumn(name = "course_id"))
    public List<String> tag;

}
