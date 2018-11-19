import java.sql.*;

public class TableChecker {
    public boolean check(Connection con, String TableName) throws SQLException {
        DatabaseMetaData dbm = con.getMetaData();
        ResultSet rs2 = dbm.getTables(null, null, TableName, null);
        if (rs2.next()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkForCompany(Connection con, String CompanyPrefix) throws SQLException {
        QueryBuilder builder = new QueryBuilder();
        PreparedStatement statement = con.prepareStatement(builder.select(new String[]{"*"},"companies","company_prefix").getQuery());
        statement.setString(1,CompanyPrefix);
        ResultSet rs = statement.executeQuery();
        if(!rs.next()){
            return false;
        }
        return true;
    }
}
