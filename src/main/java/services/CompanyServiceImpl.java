package services;

import models.DBModels.Company;
import repositories.interfacerepositories.CompanyRepository;
import repositories.implementedrepositories.CompanyRepositoryImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CompanyServiceImpl implements CompanyService{
    private CompanyRepository companyRepository;

    public CompanyServiceImpl() throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException {
        companyRepository = new CompanyRepositoryImpl();
    }

    public CompanyServiceImpl(Connection connection) throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException {
        companyRepository = new CompanyRepositoryImpl();
        companyRepository.setConnection(connection);
    }

    @Override
    public Optional<Company> getById(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return companyRepository.get(id);
    }

    @Override
    public Optional<Integer> addCompany(Company c) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return companyRepository.insert(c);
    }

    @Override
    public Optional<List<Company>> getAll() throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return companyRepository.get();
    }

    @Override
    public void updateCompany(Company c) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        companyRepository.update(c);
    }

    @Override
    public void deleteCompany(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        companyRepository.delete(id);
    }

    @Override
    public Optional<Company> getByCompanyPrefix(String companyPrefix) throws SQLException, IllegalAccessException {
        return companyRepository.getByPrefix(companyPrefix);
    }
}
