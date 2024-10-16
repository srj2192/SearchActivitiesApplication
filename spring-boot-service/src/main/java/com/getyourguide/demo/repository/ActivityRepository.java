package com.getyourguide.demo.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getyourguide.demo.model.Activity;
import com.getyourguide.demo.service.JSONResourceReaderService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class ActivityRepository {

    private List<Activity> activities;
    private final JSONResourceReaderService jsonResourceReader;

    @Autowired
    public ActivityRepository(JSONResourceReaderService jsonResourceReader) {
        this.jsonResourceReader = jsonResourceReader;
    }

    @PostConstruct
    public void loadActivities() throws IOException {
        // TODO: Can be removed to add a data store like Redis or even elastic search while moving to production
        String fileInputStream = jsonResourceReader.readJsonFile("static/activities.json");
        ObjectMapper objectMapper = new ObjectMapper();
        activities = objectMapper.readValue(fileInputStream, new TypeReference<>() {
        });
    }

    public List<Activity> getAllActivities() {
        return activities;
    }

    public List<Activity> searchActivitiesByTitle(String title) {
        if (title != null) {
            String[] splitTitleList = title.split("\\s+");
            return activities.stream()
                    .filter(activity -> Arrays.stream(splitTitleList).allMatch(splitTitle -> activity.getTitle()
                            .toLowerCase().contains(splitTitle.toLowerCase())))
                    .toList();

        }
        return Collections.emptyList();
    }
}
