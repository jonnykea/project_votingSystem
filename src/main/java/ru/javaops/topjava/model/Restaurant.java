package ru.javaops.topjava.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames =
        {"id", "registered"}, name = "restaurant_unique_id_registered_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Restaurant extends NamedEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 30, max = 300)
    private String description;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private Date registered = new Date();

    public Restaurant(Integer id, String name, Date registered, String description, String address) {
        super(id, name);
        this.registered = registered;
        this.description = description;
    }
}