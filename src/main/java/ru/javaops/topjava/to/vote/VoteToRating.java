package ru.javaops.topjava.to.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteToRating {
    String name;
    Integer rating;
}