package ru.puchinets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puchinets.model.entity.New;

import java.time.LocalDateTime;
import java.util.List;

public interface NewRepository extends JpaRepository<New, Long> {
    List<New> findAllByCreatedAfterAndCreatedBefore(LocalDateTime after, LocalDateTime before);
    void deleteAllByCreatedBefore(LocalDateTime before);
}
