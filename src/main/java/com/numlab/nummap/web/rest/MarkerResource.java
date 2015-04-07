package com.numlab.nummap.web.rest;

/**
 * Created by eisti on 4/3/15.
 */

import com.codahale.metrics.annotation.Timed;
import com.numlab.nummap.domain.Marker;
import com.numlab.nummap.domain.User;
import com.numlab.nummap.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class MarkerResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private UserRepository userRepository;



    /**
     * GET  /users -> get all markers.
     */
    @RequestMapping(value = "/markers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Marker> getAllMarkers() {
        log.debug("REST request to get all Markers");
        List<User> listUser =  userRepository.findAll();
        List<Marker> listMarkers = new LinkedList<>();

        for(User user : listUser){
           /* Pour ne pas inclure les utilisateurs qui n'ont pas de localisation, par example les admins */
            if(!(user.toMarker() == null) && user.getActivated() && user.isValidatedByAdmin())
                listMarkers.add(user.toMarker());
        }
        return(listMarkers);
    }



}
