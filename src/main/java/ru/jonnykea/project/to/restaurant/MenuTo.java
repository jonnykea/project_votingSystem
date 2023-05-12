package ru.jonnykea.project.to.restaurant;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.jonnykea.project.to.NamedTo;

@Value
@EqualsAndHashCode(callSuper = true)
public class MenuTo extends NamedTo {
    @NotNull
    Integer restaurantId;

    public MenuTo(Integer id, String name, Integer restaurantId) {
        super(id, name);
        this.restaurantId = restaurantId;
    }
}