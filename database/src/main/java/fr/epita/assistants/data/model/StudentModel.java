package fr.epita.assistants.data.model;

import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="student_model")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class StudentModel
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "name", length = 255)
    public String name;


    @Column(name = "course_id", length = 255)
    public Long course_id;
}
