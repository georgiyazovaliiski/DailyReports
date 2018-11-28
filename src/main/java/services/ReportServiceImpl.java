package services;

import models.DBModels.Department;
import models.DBModels.Employee;
import repositories.implementedrepositories.DepartmentRepositoryImpl;
import repositories.implementedrepositories.EmployeeRepositoryImpl;
import repositories.interfacerepositories.DepartmentRepository;
import repositories.interfacerepositories.EmployeeRepository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(String companyName) throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException {
        employeeRepository = new EmployeeRepositoryImpl(companyName);
    }

    @Override
    public Optional<Employee> getById(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return employeeRepository.get(id);
    }
}
