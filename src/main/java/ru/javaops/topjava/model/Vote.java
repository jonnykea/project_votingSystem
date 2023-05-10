package ru.javaops.topjava.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javaops.topjava.model.restaurant.Restaurant;
import ru.javaops.topjava.model.user.User;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "user_id"}, name = "vote_unique_rest_id_user_id_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Vote extends BaseEntity implements Serializable {

    @Column(name = "date", nullable = false, columnDefinition = "DATE default now()", updatable = false)
    @NotNull
    private LocalDate date = LocalDate.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Vote(Integer id){
        super(id);
    }
    public Vote(Integer id, LocalDate date, Restaurant restaurant, User user){
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.user = user;
    }
}
