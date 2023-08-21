package ru.kviak.coffeeorder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.coffeeorder.model.EventOrder;
import ru.kviak.coffeeorder.repository.EventRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventCreateService {

    private final EventRepository eventRepository;

    @Transactional
    public void save(EventOrder event){
        event.setTime(LocalDateTime.now());
        eventRepository.save(event);
    }
}
