package org.bridgelabz.fundoo.notes.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.*;

@Table(name="Note")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    @Column("noteId")
    private int noteId;
    @Column("note")
    private String note;
    @Column("id")
    private int id;//stores as a refrence from user table id
    @Column("archived")
    private boolean archived;
    @Column("pinned")
    private boolean pinned;
    @Column("trash")
    private boolean trash;
    @Column("title")
    private String title;

}


