package ru.jonnykea.project.to.restaurant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.jonnykea.project.to.NamedTo;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantToFrom extends NamedTo {
    @NotBlank
    @Size(min = 10, max = 100)
    String description;

    @NotBlank
    @Size(min = 10, max = 250)
    String address;

    public RestaurantToFrom(Integer id, String name, String description, String address) {
        super(id, name);
        this.description = description;
        this.address = address;
    }
}