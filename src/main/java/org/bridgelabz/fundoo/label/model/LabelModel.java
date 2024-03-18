package org.bridgelabz.fundoo.label.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "labels")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class LabelModel {
    @Id
    @Column("label_id")
    private int labelid;
    private String name;

    @Column("user_id")
    private int userId;
    @Column("note_Id")
    private int noteId;


}
