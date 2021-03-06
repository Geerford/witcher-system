package com.nevdev.witcher.services;

import com.nevdev.witcher.application.ILocationService;
import com.nevdev.witcher.core.Location;
import com.nevdev.witcher.enums.Region;
import com.nevdev.witcher.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements ILocationService {
    @Autowired
    LocationRepository locationRepository;

    @Override
    public Location find(Region regionName) {
        return locationRepository.findByRegion(regionName);
    }

    @Override
    public Location create(Location model) {
        Location location = find(model.getRegion());
        if(location == null){
            return locationRepository.saveAndFlush(model);
        }
        return location;
    }

    @Override
    public void delete(Location model) {
        locationRepository.delete(model);
    }

    @Override
    public Location edit(Location model) {
        Location location = find(model.getRegion());
        if(location == null){
            return locationRepository.save(model);
        }
        return location;
    }

    @Override
    public Location get(Long id) {
        if (id == null){
            return null;
        }
        return locationRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Location> getAll() {
        return locationRepository.findAll();
    }
}
