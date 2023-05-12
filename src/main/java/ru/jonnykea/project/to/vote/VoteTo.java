package ru.jonnykea.project.to.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteTo {
    Integer id;
    String nameRestaurant;
    String nameUser;
}