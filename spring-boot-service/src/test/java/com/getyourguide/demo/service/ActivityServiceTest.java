package com.getyourguide.demo.service;

import com.getyourguide.demo.model.Activity;
import com.getyourguide.demo.model.ActivityResponse;
import com.getyourguide.demo.model.Supplier;
import com.getyourguide.demo.repository.ActivityRepository;
import com.getyourguide.demo.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private ActivityService activityService;

    private Activity activity1;
    private Activity activity2;
    private Supplier supplier;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        activity1 = new Activity(1L, "Activity 1", 100.0, "USD", 4.5, true, 1L);
        activity2 = new Activity(2L, "Activity 2", 200.0, "EUR", 4.0, false, 2L);
        supplier = new Supplier(1L, "Supplier 1", "123 Main St", 12345, "City", "Country");
    }

    @Test
    void testGetActivities_EmptyTitle() {
        when(activityRepository.getAllActivities()).thenReturn(Arrays.asList(activity1, activity2));
        when(supplierRepository.getSupplierByID(1L)).thenReturn(Optional.of(supplier));
        when(supplierRepository.getSupplierByID(2L)).thenReturn(Optional.empty());

        List<ActivityResponse> result = activityService.getActivities("");

        assertEquals(2, result.size());
        verifyActivityResponse(result.get(0), activity1, supplier);
        verifyActivityResponse(result.get(1), activity2, null);

        verify(activityRepository).getAllActivities();
        verify(supplierRepository, times(2)).getSupplierByID(anyLong());
    }

    @Test
    void testGetActivities_WithTitle() {
        when(activityRepository.searchActivitiesByTitle("Activity 1")).thenReturn(List.of(activity1));
        when(supplierRepository.getSupplierByID(1L)).thenReturn(Optional.of(supplier));

        List<ActivityResponse> result = activityService.getActivities("Activity 1");

        assertEquals(1, result.size());
        verifyActivityResponse(result.get(0), activity1, supplier);

        verify(activityRepository).searchActivitiesByTitle("Activity 1");
        verify(supplierRepository).getSupplierByID(1L);
    }

    private void verifyActivityResponse(ActivityResponse response, Activity activity, Supplier supplier) {
        assertEquals(activity.getId(), response.getId());
        assertEquals(activity.getTitle(), response.getTitle());
        assertEquals(String.format("%s%s", activity.getPrice(), activity.getCurrency()), response.getPrice());
        assertEquals(activity.getRating(), response.getRating());
        assertEquals(activity.isSpecialOffer(), response.isSpecialOffer());

        if (supplier != null) {
            assertEquals(supplier.getName(), response.getSupplierName());
            assertEquals(String.format("%s, %s %s, %s",
                    supplier.getAddress(),
                    supplier.getCity(),
                    supplier.getZip(),
                    supplier.getCountry()), response.getSupplierLocation());
        } else {
            assertNull(response.getSupplierName());
            assertNull(response.getSupplierLocation());
        }
    }
}
