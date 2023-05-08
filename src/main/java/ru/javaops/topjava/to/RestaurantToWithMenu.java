package ru.javaops.topjava.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javaops.topjava.model.restaurant.Menu;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantToWithMenu {
    LocalDate registered;
    String name;
    String description;
    String address;
    Menu menu;
}