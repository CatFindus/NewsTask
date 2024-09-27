package ru.puchinets.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewDto {
    private Long id;
    private String header;
    private String text;
    private LocalDateTime created;

}
