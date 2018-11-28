package repositories.interfacerepositories;

import models.DBModels.Company;
import repositories.baserepositories.BaseRepository;

import java.sql.SQLException;
import java.util.Optional;

public interface CompanyRepository extends BaseRepository<Company> {
    //custom functions
    Optional<Company> getByPrefix(String companyPrefix) throws SQLException, IllegalAccessException;
}
