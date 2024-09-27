package ru.puchinets.ui.utils.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.puchinets.ui.model.New;
import ru.puchinets.ui.utils.ApiRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ApiRequestImpl implements ApiRequest {

    private final String apiAddr;
    private final ObjectMapper mapper;

    public ApiRequestImpl() {
        this.apiAddr = System.getenv("API_ADDR")==null?"http://localhost:8585/api/v1/news":System.getenv("API_ADDR");
        this.mapper = new ObjectMapper();
    }

    @Override
    public List<New> getNewsFromApi(LocalTime from, LocalTime to, String source) {
        try {
            URI uri = URI.create(apiAddr + "?from=" + from + "&to=" + to + "&source=" + source);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return mapResponse(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<New> mapResponse(String response) throws JsonProcessingException {
        return mapper.readValue(response, new TypeReference<>(){});
    }

}
