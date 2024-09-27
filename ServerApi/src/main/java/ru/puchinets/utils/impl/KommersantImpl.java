package ru.puchinets.utils.impl;

import lombok.Builder;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.puchinets.model.entity.New;
import ru.puchinets.utils.NewsParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class KommersantImpl implements NewsParser {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
    private final static String BASIC_URI = "http://www.kommersant.ru";
    @Override
    public List<New> parse(LocalDateTime from, LocalDateTime to) throws IOException {
        Document document = Jsoup
                .connect(BASIC_URI+"/lenta")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; rv:129.0) Gecko/20100101 Firefox/129.0")
                .get();
        List<ParsedItem> items = getParsedItemsFromDocument(document);
        List<New> news = mapToNews(items);
        return news;
    }

    private List<New> mapToNews(List<ParsedItem> items) {
        List<New> news = new ArrayList<>();
        for (ParsedItem item : items) {
            news.add(new New(null, item.title, item.text, item.timeStamp));
        }
        return news;
    }

    private List<ParsedItem> getParsedItemsFromDocument(Document document) throws IOException {
        List<ParsedItem> parsedItems = new ArrayList<>();
        Elements newsBlocks = document.getElementsByClass("uho__text rubric_lenta__item_text");
        for (Element element : newsBlocks) {
            String timeStamp = element.select("p").text();
            String title = element.getElementsByClass("rubric_lenta__item_name").get(0).text();
            String link = element.getElementsByClass("rubric_lenta__item_name").get(0).select("a").attr("href");
            String text = getTextByLink(link);
            LocalDateTime parsedTimeStamp = LocalDateTime.parse(timeStamp, formatter);
            ParsedItem item = ParsedItem
                    .builder()
                    .title(title)
                    .link(link)
                    .text(text)
                    .timeStamp(parsedTimeStamp)
                    .build();
            parsedItems.add(item);
        }
        return parsedItems;
    }

    private String getTextByLink(String link) throws IOException {
     Document document = Jsoup
             .connect(BASIC_URI+link)
             .userAgent("Mozilla/5.0 (Windows NT 10.0; rv:129.0) Gecko/20100101 Firefox/129.0")
             .get();
        String text = document.getElementsByClass("article_text_wrapper").get(0).text();
        return text;
    }

    @Builder
    @Data
    private static class ParsedItem {
        private String title;
        private String link;
        private String text;
        private LocalDateTime timeStamp;
    }
}
