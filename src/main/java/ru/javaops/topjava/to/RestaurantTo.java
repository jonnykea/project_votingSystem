package ru.javaops.topjava.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTo {
    String name;
    String description;
    String address;
    List<DishTo> dishes;
}