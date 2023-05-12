package ru.jonnykea.project.to.restaurant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.jonnykea.project.to.NamedTo;
import ru.jonnykea.project.util.validation.NoHtml;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantToFrom extends NamedTo {
    @NotBlank
    @NoHtml
    @Size(min = 10, max = 100)
    String description;

    @NotBlank
    @NoHtml
    @Size(min = 10, max = 250)
    String address;

    public RestaurantToFrom(Integer id, String name, String description, String address) {
        super(id, name);
        this.description = description;
        this.address = address;
    }
}