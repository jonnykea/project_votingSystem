package ru.javaops.topjava.repository.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.user.User;
import ru.javaops.topjava.repository.BaseRepository;

import java.util.Optional;

import static ru.javaops.topjava.config.SecurityConfig.PASSWORD_ENCODER;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);

    //    https://stackoverflow.com/a/46013654/548473
    @EntityGraph(attributePaths = {"meals","roles"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT u FROM User u WHERE u.id=?1")
    Optional<User> getWithMeals(int id);

    @Transactional
    default User prepareAndSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return save(user);
    }

    default User getExistedByEmail(String email) {
        return findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("User with email=" + email + " not found"));
    }
}