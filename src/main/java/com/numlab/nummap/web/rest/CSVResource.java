package com.numlab.nummap.web.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by eisti on 4/20/15.
 */

@RestController
@RequestMapping("/api")
public class CSVResource {

    @RequestMapping(value="/uploadcsv", method= RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();
                return "{ \"Response\" : \"You successfully uploaded " + name + "! \" }";
            } catch (Exception e) {
                return " { \" Response \" : \"You failed to upload  " + name + " => " + e.getMessage()+" \" }";
            }
        } else {
            return " { \"Response\" : \"You failed to upload " + name + " because the file was empty.\" }";
        }
    }

}



