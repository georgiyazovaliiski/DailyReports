package services;

import models.DBModels.City;
import models.DBModels.Department;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Optional<Department> getById(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    Optional<Integer> addDepartment(Department d) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    Optional<List<Department>> getAll() throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    void updateDepartment(Department d) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    void deleteDepartment(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    Optional<Integer> checkForDepartment(String name);
}
