package services;

import models.DBModels.City;

import java.util.List;
import java.util.Optional;

public interface CityService {
    Optional<City> getById(int id);
    Optional<Integer> addCity(City d);
    Optional<List<City>> getAll();
    void updateCity(City d);
    void deleteCity(int id);
    Optional<Integer> checkForCity(String cityName);
}
