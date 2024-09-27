package ru.puchinets.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ru.puchinets.ui.model.New;
import ru.puchinets.ui.service.NewsService;
import ru.puchinets.ui.service.impl.NewsServiceImpl;
import ru.puchinets.ui.utils.impl.ApiRequestImpl;

public class UIController {
    @FXML
    private ChoiceBox<String> dayPartSelection;
    @FXML
    private ChoiceBox<String> sourceSelection;
    @FXML
    private Label title;
    @FXML
    private Label dateTime;
    @FXML
    private TextArea textField;

    private final NewsService newsService = new NewsServiceImpl(new ApiRequestImpl());

    @FXML
    protected void getNewsButtonClick() {
        New news = newsService.getNews(sourceSelection.getValue(), dayPartSelection.getValue());
        title.setText(news.getHeader());
        dateTime.setText(news.getCreated().toString());
        textField.setText(news.getText());
    }

    @FXML
    protected void getNextButtonClick() {
        New news = newsService.nextNew();
        title.setText(news.getHeader());
        dateTime.setText(news.getCreated().toString());
        textField.setText(news.getText());
    }

    @FXML
    protected void getPrevousButtonClick() {
        New news = newsService.previousNew();
        title.setText(news.getHeader());
        dateTime.setText(news.getCreated().toString());
        textField.setText(news.getText());
    }

}