package com.getyourguide.demo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getyourguide.demo.model.Activity;
import com.getyourguide.demo.model.ActivityResponse;
import com.getyourguide.demo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/debug")
    public void debug(@RequestParam(name = "title", required = false, defaultValue = "NONE") String title,
                      Model model) {
        try {
            model.addAttribute("title", title);
            //create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            //read JSON file and convert to a list of activities
            var fileInputStream  = new ClassPathResource("static/activities.json").getInputStream();
            var activities = objectMapper.readValue(fileInputStream, new TypeReference<List<Activity>>() {
            });
            model.addAttribute("activities", activities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/activities")
    public ResponseEntity<List<ActivityResponse>> activities(
            @RequestParam(name = "title", defaultValue = "") String title) {
        List<ActivityResponse> activities = activityService.getActivities(title);

        return ResponseEntity.ok(activities);
    }
}
