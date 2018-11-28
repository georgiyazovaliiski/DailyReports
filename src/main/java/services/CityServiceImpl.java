package services;

import models.DBModels.City;
import repositories.implementedrepositories.CityRepositoryImpl;
import repositories.interfacerepositories.CityRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CityServiceImpl implements CityService {
    private CityRepository cityRepository;

    public CityServiceImpl(String CityName) throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException {
        this.cityRepository = new CityRepositoryImpl(CityName);
    }

    public CityServiceImpl(String CityName, Connection connection) throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException {
        this.cityRepository = new CityRepositoryImpl(CityName);
        this.cityRepository.setConnection(connection);
    }

    @Override
    public Optional<City> getById(int id) {
        try {
            return cityRepository.get(id);
        } catch(Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> addCity(City c) {
        try{
            return cityRepository.insert(c);
        } catch(Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<City>> getAll(){
        try {
            return cityRepository.get();
        }catch(Exception e){
            return Optional.empty();
        }
    }

    @Override
    public void updateCity(City c){
        try{
            cityRepository.update(c);
        }catch(Exception e){
            System.out.println("An error came along!");;
        }
    }

    @Override
    public void deleteCity(int id) {
        try{
            cityRepository.delete(id);
        }catch(Exception e){
            System.out.println("An error came along!");;
        }
    }

    @Override
    public Optional<Integer> checkForCity(String cityName) {
        try {
            return cityRepository.checkByName(cityName);
        }
        catch (Exception e){
            return Optional.empty();
        }
    }
}
