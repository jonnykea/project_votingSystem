package ru.jonnykea.project.to.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTo {
    Integer id;
    LocalDate registered;
    String name;
    String description;
    String address;
    String dishes;
}