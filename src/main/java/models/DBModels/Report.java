package models.DBModels;

import annotation.Table;

import java.time.LocalDate;
import java.util.Date;

@Table(name = "reports")
public class Report extends BaseEntity {
    private LocalDate report_date;
    private double report_turnover;
    private int report_employee_id;
    private int report_company_id;
    private int report_department_id;
    private int report_city_id;

    public Report(){

    }

    public Report(LocalDate report_date, double report_turnover, int report_employee_id, int report_company_id, int report_department_id) {
        this.report_date = report_date;
        this.report_turnover = report_turnover;
        this.report_employee_id = report_employee_id;
        this.report_company_id = report_company_id;
        this.report_department_id = report_department_id;
    }

    public Report(LocalDate report_date, double report_turnover, int report_employee_id, int report_company_id) {
        this.report_date = report_date;
        this.report_turnover = report_turnover;
        this.report_employee_id = report_employee_id;
        this.report_company_id = report_company_id;
    }

    public Report(int id, LocalDate report_date, double report_turnover, int report_employee_id, int report_company_id, int report_department_id, int report_city_id) {
        super(id);
        this.report_date = report_date;
        this.report_turnover = report_turnover;
        this.report_employee_id = report_employee_id;
        this.report_company_id = report_company_id;
        this.report_department_id = report_department_id;
        this.report_city_id = report_city_id;
    }

    public LocalDate getReport_date() {
        return report_date;
    }

    public void setReport_date(LocalDate report_date) {
        this.report_date = report_date;
    }

    public double getReport_turnover() {
        return report_turnover;
    }

    public void setReport_turnover(double report_turnover) {
        this.report_turnover = report_turnover;
    }

    public int getReport_employee_id() {
        return report_employee_id;
    }

    public void setReport_employee_id(int report_employee_id) {
        this.report_employee_id = report_employee_id;
    }

    public int getReport_company_id() {
        return report_company_id;
    }

    public void setReport_company_id(int report_company_id) {
        this.report_company_id = report_company_id;
    }

    public int getReport_department_id() {
        return report_department_id;
    }

    public void setReport_department_id(int report_department_id) {
        this.report_department_id = report_department_id;
    }

    public int getReport_city_id() {
        return report_city_id;
    }

    public void setReport_city_id(int report_city_id) {
        this.report_city_id = report_city_id;
    }
}
