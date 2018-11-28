package services;

import models.DBModels.Employee;
import models.DBModels.Department;
import models.DBModels.Employee;
import repositories.implementedrepositories.DepartmentRepositoryImpl;
import repositories.implementedrepositories.EmployeeRepositoryImpl;
import repositories.interfacerepositories.DepartmentRepository;
import repositories.interfacerepositories.EmployeeRepository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(String EmployeeName) throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException {
        employeeRepository = new EmployeeRepositoryImpl(EmployeeName);
    }

    public EmployeeServiceImpl(String EmployeeName, Connection connection) throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException {
        employeeRepository = new EmployeeRepositoryImpl(EmployeeName);
        employeeRepository.setConnection(connection);
    }

    @Override
    public Optional<Employee> getById(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return employeeRepository.get(id);
    }

    @Override
    public Optional<Integer> addEmployee(Employee c) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return employeeRepository.insert(c);
    }

    @Override
    public Optional<List<Employee>> getAll() throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return employeeRepository.get();
    }

    @Override
    public void updateEmployee(Employee c) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        employeeRepository.update(c);
    }

    @Override
    public void deleteEmployee(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        employeeRepository.delete(id);
    }

    @Override
    public Optional<Integer> checkForEmployee(String name) {
        try {
            return employeeRepository.checkByName(name);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
