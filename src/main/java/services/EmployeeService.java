package services;

import models.DBModels.City;
import models.DBModels.Employee;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> getById(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    Optional<Integer> addEmployee(Employee e) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    Optional<List<Employee>> getAll() throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    void updateEmployee(Employee e) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    void deleteEmployee(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    Optional<Integer> checkForEmployee(String name);
}
