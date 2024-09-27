package ru.puchinets.service;

import ru.puchinets.model.dto.NewDto;
import ru.puchinets.model.entity.New;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface NewService {
    Optional<NewDto> getNewById(Long id);
    List<NewDto> getAllNews(LocalTime from, LocalTime to, String source);
    boolean addNews(List<New> news);
    Optional<NewDto> updateNew(Long newId, NewDto newDto);
    NewDto createNew(NewDto newDto);
    boolean deleteNew(Long id);
    void deleteAllBeforeCurrent();
}
