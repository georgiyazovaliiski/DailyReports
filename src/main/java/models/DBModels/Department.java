package models.DBModels;

import annotation.Table;

@Table(name = "departments")
public class Department extends BaseEntity {
    private String department_name;
    private int department_city_id;
    private int department_company_id;

    public Department() {
    }

    public Department(String department_name, int department_city_id, int department_company_id) {
        this.department_name = department_name;
        this.department_city_id = department_city_id;
        this.department_company_id = department_company_id;
    }

    public Department(int id, String department_name, int department_city_id, int department_company_id) {
        super(id);
        this.department_name = department_name;
        this.department_city_id = department_city_id;
        this.department_company_id = department_company_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public int getDepartment_city_id() {
        return department_city_id;
    }

    public void setDepartment_city_id(int department_city_id) {
        this.department_city_id = department_city_id;
    }

    public int getDepartment_company_id() {
        return department_company_id;
    }

    public void setDepartment_company_id(int department_company_id) {
        this.department_company_id = department_company_id;
    }
}
