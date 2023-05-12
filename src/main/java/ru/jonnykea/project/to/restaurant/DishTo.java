package ru.jonnykea.project.to.restaurant;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.jonnykea.project.to.NamedTo;

@Value
@EqualsAndHashCode(callSuper = true)
public class DishTo extends NamedTo {
    @NotNull
    Integer restaurantId;

    @NotNull
    Integer price;

    public DishTo(Integer id, String name, Integer restaurantId, int price) {
        super(id, name);
        this.restaurantId = restaurantId;
        this.price = price;
    }
}