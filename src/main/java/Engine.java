import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import connectionResources.Connector;
import connectionResources.QueryBuilder;
import excelserialization.ExcelWriter;
import excelserialization.ReportPOJO;
import models.DBModels.Company;
import models.DBModels.Employee;
import models.DBModels.Report;
import models.reportcomponents.City;
import models.reportcomponents.Department;
import models.reports.BaseDailyReport;
import models.reports.CSVDailyReport;
import models.reports.XMLDailyReport;
import services.*;

import javax.xml.bind.JAXBException;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

class Settings {
    @Parameter(names = "-action", description = "Action to be done", required = true)
    private String action;

    @Parameter(names = "-url", description = "File Url", required = true)
    private String fileURL;

    @Parameter(names = "-month", description = "Number of month (1-12) for timesheet", required = true)
    private Integer month;

    @Parameter(names = "-year", description = "Year", required = true)
    private Integer year;

}

public class Engine {
    private List<Field> getClassFields(List<Field> fields, Class<?> tClass) {
        fields.addAll(Arrays.asList(tClass.getDeclaredFields()));

        if (tClass.getSuperclass() != null)
            getClassFields(fields, tClass.getSuperclass());

        return fields;
    }

    public void Run(String[] args) throws JAXBException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IOException, SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, PropertyVetoException {
        //Settings s = new Settings();

        //JCommander commander = new JCommander(s, args);


        Connector connector = new Connector();
        connector.createConnection();
        Connection con = connector.getConnection();

        QueryBuilder builder = new QueryBuilder();
        TableChecker checker = new TableChecker();

        if(!checker.check(con, "companies")) // CHECKS IF TABLE COMPANIES EXISTS
        {
            PreparedStatement stmnt = con.prepareStatement(builder.createCompanies());
            stmnt.execute();
        }

        //ReportService service = new ReportServiceImpl("brainbox", con);
        //List<ReportPOJO> reports = service.getByPeriod(LocalDate.parse("2017-01-02"),LocalDate.parse("2018-01-05")).get();

        //RUN COMMAND LINER

        parseFiles(con,builder);  // PARSE FILES

        //ExcelWriter.write(reports); // EXPORT REPORTS
    }

    private void parseFiles(Connection con, QueryBuilder builder) throws JAXBException, IOException, SQLException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        FileParser fileParser = new FileParser();
        List<BaseDailyReport> reports = fileParser.ParseList("daily-reports");

        CompanyService companyService = new CompanyServiceImpl(con);
        //String companyName = companyService.getById(2).get().getCompanyPrefix();

        for (BaseDailyReport report : reports) {
            Optional<Company> company = companyService.getByCompanyPrefix(report.getCompany().getCompanyPrefix());
            int companyId = 0;
            if(company.get().getId() == null){
                companyId = createTablesForCompany(con,report.getClass(),report.getCompany().getName(),builder);
            }else {
                companyId = company.get().getId();
            }
            CityService cityService = new CityServiceImpl(company.get().getCompanyPrefix(), con);
            DepartmentService departmentService = new DepartmentServiceImpl(company.get().getCompanyPrefix(), con);
            EmployeeService employeeService = new EmployeeServiceImpl(company.get().getCompanyPrefix(), con);
            ReportService reportService = new ReportServiceImpl(company.get().getCompanyPrefix(), con);
            persistReport(report, companyId);
        }
    }

    private void persistReport(BaseDailyReport report, int companyId) throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, InvocationTargetException {
        String companyName = report.getCompany().getName();

        CityService cityService = new CityServiceImpl(companyName);
        EmployeeService employeeService = new EmployeeServiceImpl(companyName);
        ReportService reportService = new ReportServiceImpl(companyName);
        if(report.getClass() == XMLDailyReport.class){
            //report components
            int cityId = 0;

            DepartmentService departmentService = new DepartmentServiceImpl(companyName);
            XMLDailyReport xmlReport = (XMLDailyReport)report;
            for (City c : xmlReport.getCities().getCities()) {
                Report addingReport = new Report();
                String cityName = c.getName();
                Optional<Integer> cityExists = cityService.checkForCity(cityName);

                if(!cityExists.isPresent()){
                    models.DBModels.City city = new models.DBModels.City();
                    city.setName(cityName);
                    cityId = cityService.addCity(city).get();
                }else{
                    cityId = cityExists.get();
                }

                List<Department> departments = c.getDepartments();
                for (Department department : departments) {
                    Optional<Integer> departmentExists = departmentService.checkForDepartment(department.getDepartmentName());
                    Optional<Integer> employeeExists = employeeService.checkForEmployee(department.getFullName());
                    double turnover = department.getTurnOver();
                    int departmentId = 0;

                    addingReport.setReport_city_id(cityId);
                    addingReport.setReport_date(report.getDate());
                    if(departmentExists.isPresent())
                    {   departmentId = departmentExists.get();
                        addingReport.setReport_department_id(departmentExists.get()); }
                    else{
                        models.DBModels.Department d = new models.DBModels.Department();
                        d.setDepartment_city_id(cityId);
                        d.setDepartment_company_id(companyId);
                        d.setDepartment_name(department.getDepartmentName());
                        int addedDepId = departmentService.addDepartment(d).get();
                        addingReport.setReport_department_id(addedDepId);
                        departmentId = addedDepId;
                    }
                    if(employeeExists.isPresent())
                    { addingReport.setReport_employee_id(employeeExists.get()); }
                    else{
                        Employee e = new Employee(department.getFullName(),cityId,departmentId,companyId);
                        int employeeId = employeeService.addEmployee(e).get();
                        addingReport.setReport_employee_id(employeeId);
                    }
                    addingReport.setReport_turnover(turnover);
                    addingReport.setReport_company_id(companyId); // REFACTOR WITHOUT HARDCODING!


                    //ADD REPORT TO DATABASE
                    try {
                        reportService.addReport(addingReport);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else if(report.getClass() == CSVDailyReport.class){
            CSVDailyReport csvReport = (CSVDailyReport) report;

            for (models.reportcomponents.Employee employee : csvReport.getEmployees()) {
                String cityName = employee.getCity();
                String employeeName = employee.getName();
                Double turnover = employee.getTurnover();
                cityService.checkForCity(cityName);
                int cityId = 0;
                Optional<Integer> CityChecked = cityService.checkForCity(cityName);
                if(CityChecked.isPresent()){
                    cityId = CityChecked.get();
                }
                if(cityId == 0){
                    models.DBModels.City c = new models.DBModels.City();
                    c.setName(cityName);
                    cityId = cityService.addCity(c).get();
                }

                int employeeId = 0;
                Optional<Integer> EmployeeChecked = employeeService.checkForEmployee(employeeName);
                if(EmployeeChecked.isPresent()){
                    employeeId = EmployeeChecked.get();
                }
                if(employeeId == 0){
                    Employee e = new Employee();
                    e.setEmployee_city_id(cityId);
                    e.setEmployee_company_id(companyId);
                    e.setEmployee_name(employeeName);
                    employeeId = employeeService.addEmployee(e).get();
                }
                Report addingReport = new Report(report.getDate(),turnover,employeeId,companyId);
                addingReport.setReport_city_id(cityId);
                reportService.addReport(addingReport);
            }
        }
    }

    private int createTablesForCompany(Connection con, Class reportClass, String companyName, QueryBuilder builder) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, NoSuchFieldException, InvocationTargetException {
        CompanyService service = new CompanyServiceImpl(con);
        models.DBModels.Company addingCompany = new models.DBModels.Company(companyName,companyName);
        int companyId = service.addCompany(addingCompany).get();

        PreparedStatement stmnt = con.prepareStatement(builder.createCities(companyName));
        stmnt.execute();

        if(reportClass == XMLDailyReport.class) {
            stmnt = con.prepareStatement(builder.createDepartments(companyName));
            stmnt.execute();

            stmnt = con.prepareStatement(builder.createEmployees(companyName));
            stmnt.execute();

            stmnt = con.prepareStatement(builder.createReports(companyName));
            stmnt.execute();

            stmnt = con.prepareStatement(builder.addForeignKey(companyName,companyName+"_employees","employee"));
            stmnt.execute();

            stmnt = con.prepareStatement(builder.addForeignKey(companyName,companyName+"_reports","report"));
            stmnt.execute();
        }else{
            stmnt = con.prepareStatement(builder.createEmployees(companyName));
            stmnt.execute();

            stmnt = con.prepareStatement(builder.createReports(companyName));
            stmnt.execute();
        }


        return companyId;
    }
}