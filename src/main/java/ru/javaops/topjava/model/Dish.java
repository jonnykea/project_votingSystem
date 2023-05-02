package ru.javaops.topjava.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "dish", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"id", "name"}, name = "dish_unique_id_name_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Dish extends NamedEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private Date created = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Dish(Integer id, String name, Date created) {
        super(id, name);
        this.created = created;
    }
}
