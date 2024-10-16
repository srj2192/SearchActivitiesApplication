package com.getyourguide.demo.repository;

import com.getyourguide.demo.model.Activity;
import com.getyourguide.demo.service.JSONResourceReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityRepositoryTest {

    @Mock
    private JSONResourceReaderService jsonResourceReader;

    private ActivityRepository activityRepository;

    @BeforeEach
    void setUp() throws IOException {
        String mockJson = "[{\"id\":1,\"title\":\"Hiking\"},{\"id\":2,\"title\":\"Swimming\"}]";
        when(jsonResourceReader.readJsonFile("static/activities.json")).thenReturn(mockJson);
        activityRepository = new ActivityRepository(jsonResourceReader);
        activityRepository.loadActivities();
    }

    @Test
    void testGetAllActivities() {
        List<Activity> activities = activityRepository.getAllActivities();
        assertEquals(2, activities.size());
        assertEquals("Hiking", activities.get(0).getTitle());
        assertEquals("Swimming", activities.get(1).getTitle());
    }

    @Test
    void testSearchActivitiesByTitle() {
        List<Activity> activities = activityRepository.searchActivitiesByTitle("hik");
        assertEquals(1, activities.size());
        assertEquals("Hiking", activities.get(0).getTitle());
    }

    @Test
    void testSearchActivitiesByTitleCaseInsensitive() {
        List<Activity> activities = activityRepository.searchActivitiesByTitle("SWIM");
        assertEquals(1, activities.size());
        assertEquals("Swimming", activities.get(0).getTitle());
    }

    @Test
    void testSearchActivitiesByTitleNoMatch() {
        List<Activity> activities = activityRepository.searchActivitiesByTitle("Running");
        assertTrue(activities.isEmpty());
    }

    @Test
    void testSearchActivitiesByIncorrectString() {
        List<Activity> activities = activityRepository.searchActivitiesByTitle(null);
        assertTrue(activities.isEmpty());
    }
}
