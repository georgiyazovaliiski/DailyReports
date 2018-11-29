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
import java.time.Year;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;



public class Engine {
    @Parameter(names = "-import", description = "Reads file/folder location and imports reports to DB", required = false)
    private String importFiles = "";

    @Parameter(names = "-export", description = "Exports reports for given period")
    private String exportReports = "";

    private List<Field> getClassFields(List<Field> fields, Class<?> tClass) {
        fields.addAll(Arrays.asList(tClass.getDeclaredFields()));

        if (tClass.getSuperclass() != null)
            getClassFields(fields, tClass.getSuperclass());

        return fields;
    }

    public void Run(String[] args) throws JAXBException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IOException, SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, PropertyVetoException {
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

        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(args);

        if(!importFiles.equals("")){
            System.out.println("Importing from: " + importFiles);
            System.out.println("Please wait.");
            parseFiles(con,builder,importFiles);  // PARSE FILES
            System.out.println("Operation completed.");
        }
        else if(!exportReports.equals("")){
            String[] exportReportsArgs = exportReports.split(" ");

            String companyName = exportReportsArgs[0];

            ReportService service = new ReportServiceImpl(companyName, con);

            int startMonth = 1;
            int endMonth = 1;
            int startYear = Integer.parseInt(exportReportsArgs[1]);
            int endYear = Integer.parseInt(exportReportsArgs[1]);
            int startDay = 1;
            int endDay = 1;
            YearMonth yearMonthStart;
            YearMonth yearMonthEnd;

            int Year = Integer.parseInt(exportReportsArgs[1]);

            String[] dateDetails = exportReportsArgs[2].split("");
            int parameter = Integer.parseInt(dateDetails[0]);

            switch (exportReportsArgs[2]){
                case "1q":
                case "2q":
                case "3q":
                case "4q":
                    startMonth = (parameter - 1) * 3 + 1;
                    endMonth = (parameter + 1 - 1) * 3;

                    yearMonthStart = YearMonth.of(Year,startMonth);
                    yearMonthEnd = YearMonth.of(Year,endMonth);

                    startDay = yearMonthStart.lengthOfMonth();
                    endDay = yearMonthEnd.lengthOfMonth();
                    break;
                case "1h":
                case "2h":
                    startMonth = (parameter - 1) * 6 + 1;
                    endMonth = (parameter) * 6;

                    yearMonthStart = YearMonth.of(Year,startMonth);
                    yearMonthEnd = YearMonth.of(Year,endMonth);

                    startDay = yearMonthStart.lengthOfMonth();
                    endDay = yearMonthEnd.lengthOfMonth();
                    break;
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                case "10":
                case "11":
                case "12":
                    startMonth = Integer.parseInt(exportReportsArgs[2]);
                    endMonth = Integer.parseInt(exportReportsArgs[2]);


                    yearMonthStart = YearMonth.of(Year,startMonth);
                    yearMonthEnd = YearMonth.of(Year,endMonth);

                    startDay = yearMonthStart.lengthOfMonth();
                    endDay = yearMonthEnd.lengthOfMonth();
                    break;
            }
            LocalDate startDate = LocalDate.of(startYear,startMonth,1);
            LocalDate endDate = LocalDate.of(endYear,endMonth,endDay);

            List<ReportPOJO> reports = service.getByPeriod(startDate,endDate).get();

            if(reports.size()==0){
                System.out.println("No records found by that period.");
            }else{
                ExcelWriter.write(reports); // EXPORT REPORTS
                System.out.println(reports.size() + " records were exported");
            }
        }
    }

    private void parseFiles(Connection con, QueryBuilder builder, String filePath) throws JAXBException, IOException, SQLException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        FileParser fileParser = new FileParser();
        List<BaseDailyReport> reports = fileParser.ParseList(filePath);

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