package repositories.implementedrepositories;

import models.DBModels.City;
import repositories.baserepositories.RepositoryBase;
import repositories.interfacerepositories.CityRepository;

import java.io.IOException;
import java.sql.SQLException;

public class CityRepositoryImpl extends RepositoryBase<City> implements CityRepository {
    public CityRepositoryImpl(String companyName) throws SQLException, NoSuchMethodException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        super.tableName = companyName + "_" + super.tableName;
        //System.out.println("SUPER: " + super.tableName);
    }
}
