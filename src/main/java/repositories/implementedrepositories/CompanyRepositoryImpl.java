package repositories;

import models.DBModels.Company;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CompanyRepositoryImpl extends RepositoryBase<Company> implements CompanyRepository{
    public CompanyRepositoryImpl() throws SQLException, NoSuchMethodException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
    }
}
