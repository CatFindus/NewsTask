package ru.puchinets.ui.service.impl;

import lombok.RequiredArgsConstructor;
import ru.puchinets.ui.model.New;
import ru.puchinets.ui.service.NewsService;
import ru.puchinets.ui.utils.ApiRequest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.puchinets.ui.contants.Constants.*;

@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private List<New> news;
    private int newsFlag;
    private final ApiRequest apiRequest;
    private final New emptyNew = new New(null, "News not found", "", LocalDateTime.now());

    @Override
    public New getNews(String source, String partOfDay) {
        LocalTime from = getBeginFromPartDay(partOfDay);
        LocalTime to = getEndFromPartDay(partOfDay);
        news = apiRequest.getNewsFromApi(from, to, source);
        newsFlag = 0;
        if (news==null || news.isEmpty()) news = List.of(emptyNew);
        return news.get(newsFlag);
    }

    @Override
    public New nextNew() {
        if (news==null) return emptyNew;
        newsFlag++;
        if (newsFlag >= news.size()) newsFlag = 0;
        return news.get(newsFlag);
    }

    @Override
    public New previousNew() {
        if (news==null) return emptyNew;
        newsFlag--;
        if (newsFlag < 0) newsFlag = news.size() - 1;
        return news.get(newsFlag);
    }

    private LocalTime getBeginFromPartDay(String partOfDay) {
        return switch (partOfDay) {
            case MORNING, FULL_DAY -> LocalTime.of(0, 0);
            case DAY -> LocalTime.of(12, 0);
            case EVENING -> LocalTime.of(18, 0);
            default -> LocalTime.of(9, 0);
        };
    }

    private LocalTime getEndFromPartDay(String partOfDay) {
        return switch (partOfDay) {
            case MORNING -> LocalTime.of(12, 0);
            case DAY -> LocalTime.of(18, 0);
            case EVENING, FULL_DAY -> LocalTime.of(23, 59);
            default -> LocalTime.of(21, 0);
        };
    }
}
