package com.numlab.nummap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.numlab.nummap.domain.Competence;
import com.numlab.nummap.domain.Domain;
import com.numlab.nummap.domain.User;
import com.numlab.nummap.repository.CompetenceRepository;
import com.numlab.nummap.repository.DomainRepository;
import com.numlab.nummap.security.AuthoritiesConstants;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by christo on 24/03/15.
 */
@RestController
@RequestMapping("/api")
public class FormDataResource {


    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private CompetenceRepository competenceRepository;

    @Inject
    private DomainRepository domainRepository;

    @RequestMapping(value = "/domains",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<Domain>> getDomains (){
        ArrayList<Domain> list = (ArrayList) domainRepository.findAll();
        ResponseEntity<ArrayList<Domain>> response = new ResponseEntity<ArrayList<Domain>>(list, HttpStatus.OK);
        return response;
    }


    /**
     * DELETE  /users/:login -> DELETE the "name" domain.
     */
    @RequestMapping(value = "/domains/{name}",
            method = RequestMethod.DELETE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    void deleteDomain(@PathVariable String name){
        log.debug("Rest request to Delete Domains : {}", name);
        domainRepository.deleteByName(name);
        System.out.println("Suppression du domaine "+name);
    }

    /**
     * POST /domains/:name -> ADD the "name" domain.
     */
    @RequestMapping(value = "/domains",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    void addDomain(@RequestBody Domain domain){
        log.debug("Rest request to add Domain : {}", domain);
        domainRepository.save(domain);
        System.out.println("Ajout du domaine "+domain);
    }


    /**
     * POST /competencies/:name -> ADD the "name" domain.
     */
    @RequestMapping(value = "/competencies",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    void addCompetence(@RequestBody Competence competence){
        log.debug("Rest request to add Competence : {}", competence);
        competenceRepository.save(competence);
        System.out.println("Ajout de la compétence "+competence);
    }


    @RequestMapping(value = "/competencies",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<Competence>> getCompetencies (){
        ArrayList<Competence> list = (ArrayList) competenceRepository.findAll();
        ResponseEntity<ArrayList<Competence>> response = new ResponseEntity<ArrayList<Competence>>(list, HttpStatus.OK);
        return response;
    }

    /**
     * DELETE  /users/:login -> DELETE the "name" competence.
     */
    @RequestMapping(value = "/competencies/{name}",
            method = RequestMethod.DELETE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
     void deleteCompetence(@PathVariable String name){
        log.debug("Rest request to DEL Competence : {}", name);
        competenceRepository.deleteByName(name);
    }


    /**
     * GET  /competencies/:name -> GET the "name" competence.
     */
    @RequestMapping(value = "/competencies/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    ResponseEntity<Competence> getCompetence(@PathVariable String name){
        log.debug("Rest request to GET Competence : {}", name);
        return competenceRepository.findByName(name)
                .map(competence -> new ResponseEntity<>(competence, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }



    /**
     * GET  /domains/:name -> GET the "name" domain.
     */
    @RequestMapping(value = "/domains/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    ResponseEntity<Domain> getDomain(@PathVariable String name){
        log.debug("Rest request to GET Competence : {}", name);
        return domainRepository.findByName(name)
                .map(domain -> new ResponseEntity<>(domain, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }





}
