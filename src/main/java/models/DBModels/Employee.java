package models.DBModels;

import annotation.Table;

@Table(name = "employees")
public class Employee extends BaseEntity {
    private String employee_name;
    private int employee_city_id;
    private int employee_department_id;
    private int employee_company_id;

    public Employee(){

    }

    public Employee(String employee_name, int employee_city_id, int employee_department_id, int employee_company_id) {
        this.employee_name = employee_name;
        this.employee_city_id = employee_city_id;
        this.employee_department_id = employee_department_id;
        this.employee_company_id = employee_company_id;
    }

    public Employee(int id, String employee_name, int employee_city_id, int employee_department_id, int employee_company_id) {
        super(id);
        this.employee_name = employee_name;
        this.employee_city_id = employee_city_id;
        this.employee_department_id = employee_department_id;
        this.employee_company_id = employee_company_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public int getEmployee_city_id() {
        return employee_city_id;
    }

    public void setEmployee_city_id(int employee_city_id) {
        this.employee_city_id = employee_city_id;
    }

    public int getEmployee_department_id() {
        return employee_department_id;
    }

    public void setEmployee_department_id(int employee_department_id) {
        this.employee_department_id = employee_department_id;
    }

    public int getEmployee_company_id() {
        return employee_company_id;
    }

    public void setEmployee_company_id(int employee_company_id) {
        this.employee_company_id = employee_company_id;
    }
}
