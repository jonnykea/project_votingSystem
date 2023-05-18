package ru.jonnykea.project.model.restaurant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.jonnykea.project.model.NamedEntity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menus", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"created", "restaurant_id"}, name = "menu_unique_created_r_id_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Menu extends NamedEntity {

    @Column(name = "created", nullable = false, columnDefinition = "DATE default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate created = LocalDate.now();

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu")
    @ToString.Exclude
    private List<Dish> dishes;

    public Menu(Integer id, String name, LocalDate created) {
        super(id, name);
        this.created = created;
    }

    public Menu(Integer id, String name) {
        super(id, name);
    }
}