package repositories.implementedrepositories;

import excelserialization.ReportPOJO;
import models.DBModels.Report;
import repositories.baserepositories.RepositoryBase;
import repositories.interfacerepositories.ReportRepository;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportRepositoryImpl extends RepositoryBase<Report> implements ReportRepository {
    public ReportRepositoryImpl(String companyName) throws SQLException, NoSuchMethodException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        super.tableName = companyName + "_" + super.tableName;
        //System.out.println("SUPER: " + super.tableName);
    }

    @Override
    public Optional<List<ReportPOJO>> getByPeriod(LocalDate beginDate, LocalDate endDate) throws SQLException {
        String company = tableName.split("_")[0];

        //System.out.println(builder.checkForDepartmentsTable(tableName));
        PreparedStatement stmnts = con.prepareStatement(builder.checkForDepartmentsTable(company));
        ResultSet rs2 = stmnts.executeQuery();
        int size = 0;
        if(rs2.next())
        size = rs2.getInt(1);

        String[] fields;
        if(size == 1){
            fields = new String[]{
                    company + "_reports.`id` AS \"Id\"",
                    "companies.company_name AS \"CompanyName\"",
                    company + "_cities.city_name AS \"CityName\"",
                    company + "_departments.department_name AS \"DepartmentName\"",
                    company + "_employees.employee_name AS \"EmployeeName\"",
                    "report_date AS \"Date\"",
                    "report_turnover AS \"Turnover\""};
        }
        else{
            fields = new String[]{
                    company + "_reports.`id` AS \"Id\"",
                    "companies.company_name AS \"CompanyName\"",
                    company + "_cities.city_name AS \"CityName\"",
                    company + "_employees.employee_name AS \"EmployeeName\"",
                    "report_date AS \"Date\"",
                    "report_turnover AS \"Turnover\""};
        }
        String query = super.builder.select(fields,super.tableName,"report_date",true).getQuery();
        //System.out.println("Tuk pravim querito");
        PreparedStatement stmnt = super.con.prepareStatement(query);
        stmnt.setString(1,beginDate.toString());
        stmnt.setString(2,endDate.toString());

        //System.out.println(stmnt);
        ResultSet rs = stmnt.executeQuery();

        List<ReportPOJO> reports = new ArrayList<>();

        while(rs.next()){
            String DepartmentName = "";
            String CompanyName = rs.getString("CompanyName");
            String CityName = rs.getString("CityName");
            if(rs.getMetaData().getColumnCount() == 7)
            DepartmentName = rs.getString("DepartmentName");
            int Id = rs.getInt("Id");
            String EmployeeName = rs.getString("EmployeeName");
            LocalDate Date = LocalDate.parse(rs.getString("Date"));
            Double Turnover = rs.getDouble("Turnover");

            ReportPOJO report = new ReportPOJO(Id,
                    Date,
                    Turnover,
                    EmployeeName,
                    CompanyName,
                    DepartmentName,
                    CityName);

            reports.add(report);
        }
        return Optional.of(reports);
    }
}
