package ru.jonnykea.project.model.restaurant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.jonnykea.project.model.NamedEntity;
import ru.jonnykea.project.util.validation.NoHtml;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames =
        {"name", "address"}, name = "restaurant_unique_name_address_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Restaurant extends NamedEntity {

    @Column(name = "description", nullable = false)
    @NotBlank
    @NoHtml
    @Size(min = 10, max = 100)
    private String description;

    @Column(name = "address", nullable = false)
    @NotBlank
    @NoHtml
    @Size(min = 10, max = 250)
    private String address;

    @Column(name = "registered", nullable = false, columnDefinition = "DATE default now()", updatable = false)
    @NotNull
    private LocalDate registered = LocalDate.now();

    public Restaurant(Integer id, String name, LocalDate registered, String description, String address) {
        this(id, name, description, address);
        this.registered = registered;
    }

    public Restaurant(Integer id, String name, String description, String address) {
        super(id, name);
        this.description = description;
        this.address = address;
    }

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.registered, r.description, r.address);
    }
}