package ru.javaops.topjava.web.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.model.user.User;
import ru.javaops.topjava.service.vote.VoteService;
import ru.javaops.topjava.to.vote.VoteTo;
import ru.javaops.topjava.to.vote.VoteToRating;
import ru.javaops.topjava.web.AuthUser;
import ru.javaops.topjava.web.user.AbstractUserController;

import java.net.URI;
import java.util.List;

import static ru.javaops.topjava.util.validation.ValidationUtil.checkOwner;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends AbstractUserController {
    static final String REST_URL = "/api/vote";

    @Autowired
    protected VoteService service;

    @GetMapping()
    public List<VoteTo> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    @GetMapping("/rating")
    public List<VoteToRating> getRating() {
        log.info("getRating");
        return service.getRating();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> vote(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        log.info("vote {}", restaurantId);
        User user = authUser.getUser();
        Vote vote = new Vote(null);
        Vote created = service.create(vote, user.getId(), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void reVote(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId, @PathVariable int id) {
        log.info("reVote {}", id);
        User user = authUser.getUser();
        int userId = user.getId();
        Vote vote = service.getByUserId(userId);
        checkOwner(id, vote.getId());
        service.create(vote, userId, restaurantId);
    }
}