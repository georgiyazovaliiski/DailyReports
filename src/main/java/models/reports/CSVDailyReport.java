package models.reports;

import models.reportcomponents.Employee;

import java.util.List;

public class CSVDailyReport extends BaseDailyReport{
    private List<Employee> Employees;

    public List<Employee> getEmployees() {
        return Employees;
    }

    public void setEmployees(List<Employee> employees) {
        Employees = employees;
    }
}
