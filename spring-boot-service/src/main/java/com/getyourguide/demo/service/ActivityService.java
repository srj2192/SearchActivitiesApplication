package com.getyourguide.demo.service;

import com.getyourguide.demo.model.Activity;
import com.getyourguide.demo.model.ActivityResponse;
import com.getyourguide.demo.model.Supplier;
import com.getyourguide.demo.repository.ActivityRepository;
import com.getyourguide.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    public List <ActivityResponse> getActivities(String title) {
        List<ActivityResponse> activitiesResponse = new ArrayList<>();
        List<Activity> activities = title.isEmpty() ?
                activityRepository.getAllActivities() :
                activityRepository.searchActivitiesByTitle(title);
        for(Activity activity: activities) {
            Optional<Supplier> optionalSupplier = supplierRepository.getSupplierByID(activity.getSupplierId());
            ActivityResponse activityResponse = new ActivityResponse();
            activityResponse.setId(activity.getId());
            activityResponse.setTitle(activity.getTitle());
            activityResponse.setPrice(String.format("%s%s", activity.getPrice(), activity.getCurrency()));
            activityResponse.setRating(activity.getRating());
            activityResponse.setSpecialOffer(activity.isSpecialOffer());
            if (optionalSupplier.isPresent()){
                Supplier supplier = optionalSupplier.get();
                activityResponse.setSupplierName(supplier.getName());
                activityResponse.setSupplierLocation(String.format("%s, %s %s, %s",
                        supplier.getAddress(),
                        supplier.getCity(),
                        supplier.getZip(),
                        supplier.getCountry()));
            }
            activitiesResponse.add(activityResponse);
        }
        return activitiesResponse;
    }
}
