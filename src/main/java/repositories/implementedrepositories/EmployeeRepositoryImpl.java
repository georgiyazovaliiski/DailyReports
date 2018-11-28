package repositories.implementedrepositories;

import models.DBModels.City;
import models.DBModels.Department;
import repositories.baserepositories.RepositoryBase;
import repositories.interfacerepositories.CityRepository;
import repositories.interfacerepositories.DepartmentRepository;

import java.io.IOException;
import java.sql.SQLException;

public class DepartmentRepositoryImpl extends RepositoryBase<Department> implements DepartmentRepository {
    public DepartmentRepositoryImpl(String companyName) throws SQLException, NoSuchMethodException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        super.tableName = companyName + "_" + super.tableName;
        //System.out.println("SUPER: " + super.tableName);
    }
}
