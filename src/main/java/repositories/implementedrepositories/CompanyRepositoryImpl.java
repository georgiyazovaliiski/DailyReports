package repositories.implementedrepositories;

import connectionResources.QueryInfo;
import models.DBModels.Company;
import repositories.baserepositories.RepositoryBase;
import repositories.interfacerepositories.CompanyRepository;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyRepositoryImpl extends RepositoryBase<Company> implements CompanyRepository {
    public CompanyRepositoryImpl() throws SQLException, NoSuchMethodException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
    }

    @Override
    public Optional<Company> getByPrefix(String companyPrefix) throws SQLException, IllegalAccessException {
        String tableName = this.tableName;
        QueryInfo query = builder.select(new String[]{"*"},tableName,"company_prefix");
        //System.out.println(query.getQuery());
        PreparedStatement stmnt = super.con.prepareStatement(query.getQuery(),
                Statement.RETURN_GENERATED_KEYS);

        stmnt.setString(1,companyPrefix);

        ResultSet rs = stmnt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();


        Company c = new Company();
        //System.out.println("TUK: " + Entity);
        if(rs.next()){
            List<Field> fields = super.getClassFields(new ArrayList<Field>(),typeParameterClass);

            for (Field field : fields) {
                field.setAccessible(true);
                field.set(c, rs.getObject(field.getName(), field.getType()));
            }
        }
        return Optional.ofNullable(c);
    }
}
