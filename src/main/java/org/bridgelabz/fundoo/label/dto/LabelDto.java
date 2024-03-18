package org.bridgelabz.fundoo.label.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class LabelDto {
    private String name;
    private int userId;
    private int noteId;



}
