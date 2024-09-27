package ru.puchinets.ui.utils;

import ru.puchinets.ui.model.New;

import java.time.LocalTime;
import java.util.List;

public interface ApiRequest {
    List<New> getNewsFromApi(LocalTime from, LocalTime to, String source);
}
