package ru.jonnykea.project.to.restaurant;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.jonnykea.project.to.NamedTo;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class MenuTo extends NamedTo {
    @NotNull
    Integer restaurantId;

    @NotNull
    LocalDate created;

    public MenuTo(Integer id, String name, Integer restaurantId, LocalDate created) {
        super(id, name);
        this.restaurantId = restaurantId;
        this.created = created == null ? LocalDate.now() : created;
    }
}