package excelserialization;

import java.time.LocalDate;

public class ReportPOJO {
    private int Id;
    private LocalDate Date;
    private double Turnover;
    private String EmployeeName;
    private String CompanyName;
    private String DepartmentName;
    private String CityName;

    public ReportPOJO(){

    }

    public ReportPOJO(int id, LocalDate date, double turnover, String employeeName, String companyName, String departmentName, String cityName) {
        Id = id;
        Date = date;
        Turnover = turnover;
        EmployeeName = employeeName;
        CompanyName = companyName;
        DepartmentName = departmentName;
        CityName = cityName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public double getTurnover() {
        return Turnover;
    }

    public void setTurnover(double turnover) {
        Turnover = turnover;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }
}
