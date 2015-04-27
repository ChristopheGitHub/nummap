package com.numlab.nummap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.numlab.nummap.domain.Domain;
import com.numlab.nummap.domain.School;
import com.numlab.nummap.repository.SchoolRepository;
import com.numlab.nummap.security.AuthoritiesConstants;
import com.numlab.nummap.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * Created by christo on 26/04/15.
 */
@RestController
@RequestMapping("/api/")
public class SchoolResource {

    private final Logger log = LoggerFactory.getLogger(SchoolResource.class);

    @Inject
    private SchoolRepository schoolRepository;

    @Inject
    private LocationService locationService;

    /**
     * GET /schools -> get all schools
     */
    @RequestMapping(value = "/schools",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<School>> getAll() {
        log.debug("REST request to get all Schools");
        List<School> list = schoolRepository.findAll();
        ResponseEntity<List<School>> res = new ResponseEntity<List<School>>(list, HttpStatus.OK);
        return res;
    }

    /**
     * DELETE  /schools/:login -> DELETE the "name" schools.
     */
    @RequestMapping(value = "/schools/{name}",
        method = RequestMethod.DELETE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    void deleteDomain(@PathVariable String name){
        log.debug("Rest request to Delete school : {}", name);
        schoolRepository.deleteByName(name);
        log.debug("Removing school : " + name);
    }

    /**
     * POST /schools/:name -> ADD the "name" school.
     */
    @RequestMapping(value = "/schools",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    void addDomain(@RequestBody School school){

        log.debug("Rest request to add school : {}", school);

        Optional<School> opt = schoolRepository.findOneByName(school.getName());
        if(opt.isPresent()){
            opt.map(s -> {
                s.setName(school.getName());
                schoolRepository.save(s);
                log.debug("Changed Information for Domain: {}", s);
                return new ResponseEntity<String>(HttpStatus.OK);
            });
        }
        else{
            schoolRepository.save(school);
            System.out.println("Ajout du schoole "+school);
        }
    }

}
