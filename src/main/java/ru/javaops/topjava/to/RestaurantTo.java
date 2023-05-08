package ru.javaops.topjava.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javaops.topjava.model.restaurant.Menu;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTo {
    String name;
    LocalDate registered;
    String description;
    String address;
    Menu menu;
}