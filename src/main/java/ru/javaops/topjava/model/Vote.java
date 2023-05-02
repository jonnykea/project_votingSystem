package ru.javaops.topjava.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "user_id"}, name = "vote_unique_rest_id_user_id_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Vote extends BaseEntity implements Serializable {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
