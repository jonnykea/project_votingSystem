package ru.javaops.topjava.model.restaurant;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javaops.topjava.model.NamedEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "menu", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"name", "restaurant_id"}, name = "menu_unique_name_r_id_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Menu extends NamedEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @Size(min = 2, max = 5)
    private List<Dish> dishes;

    public Menu(Integer id, String name) {
        super(id, name);
    }
}