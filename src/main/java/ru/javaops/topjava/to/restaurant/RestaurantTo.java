package ru.javaops.topjava.to.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTo {
    String name;
    String description;
    String address;
    String dishes;
}