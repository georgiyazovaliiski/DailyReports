package repositories.implementedrepositories;

import models.DBModels.Department;
import models.DBModels.Employee;
import repositories.baserepositories.RepositoryBase;
import repositories.interfacerepositories.DepartmentRepository;
import repositories.interfacerepositories.EmployeeRepository;

import java.io.IOException;
import java.sql.SQLException;

public class EmployeeRepositoryImpl extends RepositoryBase<Employee> implements EmployeeRepository {
    public EmployeeRepositoryImpl(String companyName) throws SQLException, NoSuchMethodException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        super.tableName = companyName + "_" + super.tableName;
        //System.out.println("SUPER: " + super.tableName);
    }
}
