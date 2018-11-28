package services;

import models.DBModels.Company;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CompanyService {
    // TODO: DECLARE SERVICE FUNCTIONS!
    Optional<Company> getById(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    Optional<Integer> addCompany(Company c) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    Optional<List<Company>> getAll() throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    void updateCompany(Company c) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    void deleteCompany(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    Optional<Company> getByCompanyPrefix(String companyPrefix) throws SQLException, IllegalAccessException;
}