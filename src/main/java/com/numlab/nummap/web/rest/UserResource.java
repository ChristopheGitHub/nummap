package com.numlab.nummap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.numlab.nummap.domain.User;
import com.numlab.nummap.repository.UserRepository;
import com.numlab.nummap.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private UserRepository userRepository;

    /**
     * GET  /users -> get all users.
     */
    @RequestMapping(value = "/users",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<User> getAll() {
        log.debug("REST request to get all Users");
        return userRepository.findAll();
    }

    /**
     * GET  /users/:login -> get the "login" user.
     */
    @RequestMapping(value = "/users/{login}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    ResponseEntity<User> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return userRepository.findOneByLogin(login)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

        /**
        * DELETE  /users/:login -> DELETE the "login" user.
        */
    @RequestMapping(value = "/users/{login}",
            method = RequestMethod.DELETE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    void deleteUser(@PathVariable String login){
        log.debug("Rest request to Delete User : {}", login);
        userRepository.deleteByLogin(login);
        System.out.println("Suppression de l'utilisateur "+login);
    }



    /**
     * POST  /users/validate/:login -> validate the "login" user.
     */
    @RequestMapping(value = "/users/validate/{login}",
            method = RequestMethod.POST)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    void validateUser(@PathVariable String login){
        log.debug("Rest request to Validate User : {}", login);
        userRepository.findOneByLogin(login).ifPresent(u -> {
            u.setValidatedByAdmin(true);
            userRepository.save(u);
            log.debug("User Validated : {}", u);
        });
    }

}
