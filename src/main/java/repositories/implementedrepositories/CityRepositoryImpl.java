package repositories.implementedrepositories;

import connectionResources.QueryInfo;
import models.DBModels.City;
import repositories.baserepositories.RepositoryBase;
import repositories.interfacerepositories.CityRepository;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityRepositoryImpl extends RepositoryBase<City> implements CityRepository {
    public CityRepositoryImpl(String companyName) throws SQLException, NoSuchMethodException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        super.tableName = companyName + "_" + super.tableName;
        //System.out.println("SUPER: " + super.tableName);
    }

    @Override
    public Optional<Integer> checkByName(String name) throws SQLException, IllegalAccessException {
        String tableName = this.tableName;
        QueryInfo query = builder.select(new String[]{"*"},tableName,"city_name");
        //System.out.println(query.getQuery());
        PreparedStatement stmnt = con.prepareStatement(query.getQuery(),
                Statement.RETURN_GENERATED_KEYS);

        stmnt.setString(1,name);

        ResultSet rs = stmnt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();


        City Entity = new City();
        //System.out.println("TUK: " + Entity);
        if(rs.next()){
            List<Field> fields = getClassFields(new ArrayList<Field>(),typeParameterClass);

            for (Field field : fields) {
                field.setAccessible(true);
                field.set(Entity, rs.getObject(field.getName(), field.getType()));
            }
        }
        return Optional.ofNullable(Entity.getId());
    }
}
