package services;

import models.DBModels.Company;
import models.DBModels.Department;
import repositories.implementedrepositories.CompanyRepositoryImpl;
import repositories.implementedrepositories.DepartmentRepositoryImpl;
import repositories.interfacerepositories.CompanyRepository;
import repositories.interfacerepositories.DepartmentRepository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Optional;

public class DepartmentServiceImpl implements DepartmentService{
    private DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(String companyName) throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException {
        departmentRepository = new DepartmentRepositoryImpl(companyName);
    }

    @Override
    public Optional<Department> getById(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return departmentRepository.get(id);
    }
}
