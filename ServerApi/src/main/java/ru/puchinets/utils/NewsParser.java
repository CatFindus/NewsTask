package ru.puchinets.utils;

import ru.puchinets.model.entity.New;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface NewsParser {
    List<New> parse(LocalDateTime from, LocalDateTime to) throws IOException;
}
