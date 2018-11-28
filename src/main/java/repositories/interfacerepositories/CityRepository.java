package repositories.interfacerepositories;

import models.DBModels.City;
import repositories.baserepositories.BaseRepository;

import java.sql.SQLException;
import java.util.Optional;

public interface CityRepository extends BaseRepository<City> {
    //custom functions
    Optional<Integer> checkByName(String name) throws SQLException, IllegalAccessException;
}
