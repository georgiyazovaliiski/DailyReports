package repositories.interfacerepositories;

import models.DBModels.Department;
import models.DBModels.Employee;
import repositories.baserepositories.BaseRepository;

import java.sql.SQLException;
import java.util.Optional;

public interface EmployeeRepository extends BaseRepository<Employee> {
    //custom functions
    Optional<Integer> checkByName(String name) throws SQLException, IllegalAccessException;
}
