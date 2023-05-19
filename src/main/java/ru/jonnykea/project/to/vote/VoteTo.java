package ru.jonnykea.project.to.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteTo {
    Integer id;
    LocalDate date;
    String nameRestaurant;
}