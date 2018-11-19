import models.DBModels.Company;
import models.reports.BaseDailyReport;
import models.reports.CSVDailyReport;
import models.reports.XMLDailyReport;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Engine {
    private List<Field> getClassFields(List<Field> fields, Class<?> tClass) {
        fields.addAll(Arrays.asList(tClass.getDeclaredFields()));

        if (tClass.getSuperclass() != null)
            getClassFields(fields, tClass.getSuperclass());

        return fields;
    }

    public void Run() throws JAXBException, IOException, SQLException, ClassNotFoundException {
        Connector connector = new Connector();
        connector.createConnection();
        Connection con = connector.getConnection();

        QueryBuilder builder = new QueryBuilder();
        TableChecker checker = new TableChecker();

        if(!checker.check(con, "companies")) // CHECKS IF TABLE EXISTS
        {
            PreparedStatement stmnt = con.prepareStatement(builder.createCompanies());
            stmnt.execute();
        }

        FileParser fileParser = new FileParser();
        List<BaseDailyReport> reports = fileParser.ParseList();
        for (BaseDailyReport report : reports) {
            if(!checker.checkForCompany(con,report.getCompany().getCompanyPrefix())){
                PreparedStatement stmnt = con.prepareStatement(builder.insert("companies", getClassFields(new ArrayList<>(), Company.class)).getQuery());

                    stmnt.setString(1,report.getCompany().getCompanyPrefix());
                    stmnt.setString(2,report.getCompany().getName());
                    stmnt.execute();

                    stmnt = con.prepareStatement(builder.createCities(report.getCompany().getCompanyPrefix()));
                    stmnt.execute();

                    stmnt = con.prepareStatement(builder.createEmployees(report.getCompany().getCompanyPrefix()));
                    stmnt.execute();

                    stmnt = con.prepareStatement(builder.createReports(report.getCompany().getCompanyPrefix()));
                    stmnt.execute();

                if(report.getClass() == XMLDailyReport.class) {
                    stmnt = con.prepareStatement(builder.createDepartments(report.getCompany().getCompanyPrefix()));
                    stmnt.execute();
                }
            }
        }
    }
}