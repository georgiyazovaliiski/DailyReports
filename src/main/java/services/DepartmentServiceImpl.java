package services;

import models.DBModels.Department;
import models.DBModels.Department;
import repositories.implementedrepositories.DepartmentRepositoryImpl;
import repositories.implementedrepositories.DepartmentRepositoryImpl;
import repositories.interfacerepositories.DepartmentRepository;
import repositories.interfacerepositories.DepartmentRepository;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DepartmentServiceImpl implements DepartmentService{
    private DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(String DepartmentName) throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException {
        departmentRepository = new DepartmentRepositoryImpl(DepartmentName);
    }

    public DepartmentServiceImpl(String DepartmentName, Connection connection) throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException {
        departmentRepository = new DepartmentRepositoryImpl(DepartmentName);
        departmentRepository.setConnection(connection);
    }

    @Override
    public Optional<Department> getById(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return departmentRepository.get(id);
    }

    @Override
    public Optional<Integer> addDepartment(Department c) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return departmentRepository.insert(c);
    }

    @Override
    public Optional<List<Department>> getAll() throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return departmentRepository.get();
    }

    @Override
    public void updateDepartment(Department c) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        departmentRepository.update(c);
    }

    @Override
    public void deleteDepartment(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        departmentRepository.delete(id);
    }

    @Override
    public Optional<Integer> checkForDepartment(String name) {
        try {
            return departmentRepository.checkByName(name);
        } catch (SQLException | IllegalAccessException e) {
            return Optional.empty();
        }
    }
}
