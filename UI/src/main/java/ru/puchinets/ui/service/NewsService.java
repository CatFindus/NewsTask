package ru.puchinets.ui.service;

import ru.puchinets.ui.model.New;

public interface NewsService {

    New getNews(String source, String partOfDay);
    New nextNew();
    New previousNew();
}
