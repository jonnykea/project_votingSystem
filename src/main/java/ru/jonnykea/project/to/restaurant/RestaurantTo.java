package ru.jonnykea.project.to.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTo {
    Integer id;
    String name;
    String description;
    String address;
    String dishes;
}