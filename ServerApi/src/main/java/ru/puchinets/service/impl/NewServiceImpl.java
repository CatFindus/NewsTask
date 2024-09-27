package ru.puchinets.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.puchinets.mapper.NewMapper;
import ru.puchinets.model.dto.NewDto;
import ru.puchinets.model.entity.New;
import ru.puchinets.repository.NewRepository;
import ru.puchinets.service.NewService;
import ru.puchinets.utils.NewsParser;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class NewServiceImpl implements NewService {

    private final NewRepository newRepository;
    private final NewMapper newMapper;
    private final NewsParser newsParser;


    @Override
    public Optional<NewDto> getNewById(Long id) {
        return newRepository
                .findById(id)
                .map(newMapper::entityToDto);
    }

    //todo: Select source
    @Override
    public List<NewDto> getAllNews(LocalTime from, LocalTime to, String source) {
        return newRepository
                .findAllByCreatedAfterAndCreatedBefore(from.atDate(LocalDate.now()), to.atDate(LocalDate.now()))
                .stream()
                .map(newMapper::entityToDto)
                .toList();
    }
    @Transactional
    @Override
    public boolean addNews(List<New> news) {
        newRepository.saveAll(news);
        return true;
    }

    @Override
    public Optional<NewDto> updateNew(Long newId, NewDto newDto) {
        return newRepository
                .findById(newId)
                .map(entity -> newMapper.update(entity, newDto))
                .map(newMapper::entityToDto);
    }

    @Override
    public NewDto createNew(NewDto newDto) {
        New entity = newMapper.dtoToEntity(newDto);
        entity = newRepository.save(entity);
        return newMapper.entityToDto(entity);
    }

    @Override
    public boolean deleteNew(Long id) {
        boolean isExist = newRepository.existsById(id);
        newRepository.deleteById(id);
        return isExist;
    }
    @Scheduled(cron = "0 5 0 * * *")
    @Override
    public void deleteAllBeforeCurrent() {
        LocalDate curDate = LocalDate.now();
        newRepository.deleteAllByCreatedBefore(curDate.atTime(0,0));
    }
    @Scheduled(fixedDelay = 20, timeUnit = TimeUnit.MINUTES)
    @Transactional
    protected void checkNews() {
        LocalDateTime now = LocalDateTime.now();
        List<New> newsToAdd;
        try {
            newsToAdd = newsParser.parse(now.minusMinutes(20), now);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newRepository.saveAll(newsToAdd);
    }
}
