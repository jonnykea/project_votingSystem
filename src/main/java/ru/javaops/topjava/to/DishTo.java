package ru.javaops.topjava.to;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;


@Value
@EqualsAndHashCode(callSuper = true)
public class DishTo extends NamedTo {
    @NotNull
    Integer restaurantId;

    @Column(name = "price", nullable = false)
    @NotNull
    Integer price;

    public DishTo(Integer id, String name, Integer restaurantId, int price) {
        super(id, name);
        this.restaurantId = restaurantId;
        this.price = price;
    }
}