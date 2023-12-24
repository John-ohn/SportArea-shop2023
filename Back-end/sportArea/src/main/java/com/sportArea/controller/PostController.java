package com.sportArea.controller;


import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/v1/post")
public class PostController {


    @Autowired
    private GeneralLogg generalLogg ;

    @Autowired
    private PostService postService;

    @GetMapping("/nova/region")
    public ResponseEntity<List<String>> getRegions() throws IOException {
        List<String> region = postService.getRegions();

        generalLogg.getLoggerControllerInfo("PostController",
                "getRegions",
                "/nova/region",
                "List of regions");

        return ResponseEntity.ok(region);
    }

    @GetMapping("/nova/city")
    public ResponseEntity<Set<String>> getCity(
            @RequestParam("areaDescription") String areaDescription,
            @RequestParam("findByString") String findByString) throws IOException {

        Set<String> region = postService.getCity(areaDescription, findByString);

        generalLogg.getLoggerControllerInfo("PostController",
                "getCity",
                "/nova/city",
                "List of city's");

        return ResponseEntity.ok(region);
    }

    @GetMapping("/nova/department")
    public ResponseEntity<Set<String>> getDepartment(@RequestParam("citeName") String citeName) {
        Set<String> region = postService.getDepartment(citeName);

        generalLogg.getLoggerControllerInfo("PostController",
                "getDepartment",
                "/nova/department",
                "List of departments");

        return ResponseEntity.ok(region);
    }

    @GetMapping("/nova/poshtomat")
    public ResponseEntity<Set<String>> getPoshtomat(@RequestParam("citeName") String citeName) {
        Set<String> region = postService.getPoshtomat(citeName);

        generalLogg.getLoggerControllerInfo("PostController",
                "getPoshtomat",
                "/nova/poshtomat",
                "List of poshtomats");

        return ResponseEntity.ok(region);
    }
}
