package ru.puchinets.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.puchinets.model.dto.NewDto;
import ru.puchinets.service.NewService;

import java.time.LocalTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/news")
public class NewController {
    private final NewService newService;

    @GetMapping
    ResponseEntity<List<NewDto>> getAll(@RequestParam LocalTime from, @RequestParam LocalTime to, String source) {
        return ResponseEntity.ok(newService.getAllNews(from, to, source));
    }

    @GetMapping("/{id}")
    ResponseEntity<NewDto> getById(@RequestParam("id") Long id) {
        return newService.getNewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<NewDto> create(@RequestBody NewDto newDto) {
        return new ResponseEntity<>(newService.createNew(newDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<NewDto> update(@RequestBody NewDto newDto, @PathVariable("id") Long id) {
        return newService.updateNew(id, newDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
