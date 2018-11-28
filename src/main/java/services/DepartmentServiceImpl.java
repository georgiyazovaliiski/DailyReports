package services;

import models.DBModels.Company;
import repositories.interfacerepositories.CompanyRepository;
import repositories.implementedrepositories.CompanyRepositoryImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Optional;

public class CompanyServiceImpl implements CompanyService{
    private CompanyRepository companyRepository;

    public CompanyServiceImpl() throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException {
        companyRepository = new CompanyRepositoryImpl();
    }

    @Override
    public Optional<Company> getById(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return companyRepository.get(id);
    }
}
