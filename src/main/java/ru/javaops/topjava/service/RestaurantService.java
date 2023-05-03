package ru.javaops.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import ru.javaops.topjava.model.restaurant.Restaurant;
import ru.javaops.topjava.repository.restaurant.RestaurantRepository;
import ru.javaops.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant get(int id) {
        return repository.getExisted(id);
    }

    public List<Restaurant> getAll() {
        return repository.getAll().
                stream()
                .filter(d -> d.getRegistered().compareTo(DateTimeUtil.asDate(LocalDate.now()))==0)
                .toList();
    }

    public Restaurant getByName(String name) {
        return repository.getByName(name);
    }

    public void delete(int id) {
        repository.deleteExisted(id);
    }

    public void update(Restaurant r) {
        repository.save(r);
    }
}

