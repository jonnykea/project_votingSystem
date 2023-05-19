package ru.jonnykea.project.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jonnykea.project.model.Vote;
import ru.jonnykea.project.model.user.User;
import ru.jonnykea.project.service.vote.VoteService;
import ru.jonnykea.project.to.vote.VoteTo;
import ru.jonnykea.project.to.vote.VoteToRating;
import ru.jonnykea.project.util.validation.ValidationUtil;
import ru.jonnykea.project.web.AuthUser;
import ru.jonnykea.project.web.user.AbstractUserController;

import java.net.URI;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends AbstractUserController {
    static final String REST_URL = "/api/votes";

    protected VoteService service;

    @GetMapping()
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll");
        User user = authUser.getUser();
        int userId = user.getId();
        return service.getAll(userId);
    }

    @GetMapping("/rating/by-today")
    public List<VoteToRating> getRatingByToday() {
        log.info("getRating");
        return service.getRating();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> vote(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        log.info("vote {}", restaurantId);
        User user = authUser.getUser();
        Vote vote = new Vote(null);
        Vote created = service.save(vote, user.getId(), restaurantId);
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
        ValidationUtil.checkOwner(id, vote.getId());
        service.save(vote, userId, restaurantId);
    }
}