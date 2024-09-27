package ru.puchinets.ui.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class New {
    private Long id;
    private String header;
    private String text;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime created;
}
