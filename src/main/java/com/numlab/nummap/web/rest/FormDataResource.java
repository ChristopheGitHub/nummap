package com.numlab.nummap.web.rest;

import com.numlab.nummap.domain.Competence;
import com.numlab.nummap.domain.Domain;
import com.numlab.nummap.repository.CompetenceRepository;
import com.numlab.nummap.repository.DomainRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by christo on 24/03/15.
 */
@RestController
@RequestMapping("/api")
public class FormDataResource {

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

    @RequestMapping(value = "/competencies",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<Competence>> getCompetencies (){

        ArrayList<Competence> list = (ArrayList) competenceRepository.findAll();

        ResponseEntity<ArrayList<Competence>> response = new ResponseEntity<ArrayList<Competence>>(list, HttpStatus.OK);

        return response;
    }




}
