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
    public Optional<Employee> getById(int id) {
        try{
        return employeeRepository.get(id);
        }catch (Exception e){
            System.out.println(e.toString());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> addEmployee(Employee c) {
        try{
        return employeeRepository.insert(c);
        }catch (Exception e){
            System.out.println(e.toString());
            return Optional.empty();
        }

    }

    @Override
    public Optional<List<Employee>> getAll() {
        try{
            return employeeRepository.get();
        }catch (Exception e){
            System.out.println(e.toString());
            return Optional.empty();
        }
    }

    @Override
    public void updateEmployee(Employee c) {
        try{
            employeeRepository.update(c);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    @Override
    public void deleteEmployee(int id) {
        try{
            employeeRepository.delete(id);
        }catch (Exception e){
            System.out.println(e.toString());
        }
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
