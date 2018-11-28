package repositories.interfacerepositories;

import models.DBModels.City;
import models.DBModels.Department;
import repositories.baserepositories.BaseRepository;

import java.sql.SQLException;
import java.util.Optional;

public interface DepartmentRepository extends BaseRepository<Department> {
    //custom functions
    Optional<Integer> checkByName(String name) throws SQLException, IllegalAccessException;
}
