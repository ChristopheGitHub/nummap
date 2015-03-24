package com.numlab.nummap.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.numlab.nummap.domain.Address;
import com.numlab.nummap.domain.Location;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by christo on 24/03/15.
 * Service class retrieving the coordinates of a place.
 */
@Service
public class LocationService {

    public Location getLocationFromAddress(Address address){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://maps.googleapis.com/maps/api/geocode/json?address={address}&sensor=false";

        String json = restTemplate.getForObject(url, String.class, address.toMapSearchFormat());

        ObjectMapper mapper = new ObjectMapper();
        Location location = new Location(0,0);
        try {
            final JsonNode response = mapper.readTree(json)
                .path("results")
                .path(0)
                .path("geometry")
                .path("location");
            DoubleNode lat = (DoubleNode)response.path("lat");
            DoubleNode lng = (DoubleNode)response.path("lng");
            location.setLatitude(lat.doubleValue());
            location.setLongitude(lng.doubleValue());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return location;
    }
}
