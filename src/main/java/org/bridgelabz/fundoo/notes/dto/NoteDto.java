package org.bridgelabz.fundoo.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NoteDto {

    private String note;
    private boolean archived;
    private boolean pinned;
    private boolean trash;
    private String title;

}
