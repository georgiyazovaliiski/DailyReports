package models.DBModels;

public class EmployeeWithDepartment extends Employee {
    private int employee_department_id;

    public EmployeeWithDepartment(String employee_name, int employee_city_id, int employee_department_id, int employee_company_id, int employee_department_id1) {
        super(employee_name, employee_city_id, employee_department_id, employee_company_id);
        this.employee_department_id = employee_department_id1;
    }

    public EmployeeWithDepartment(int id, String employee_name, int employee_city_id, int employee_department_id, int employee_company_id, int employee_department_id1) {
        super(id, employee_name, employee_city_id, employee_department_id, employee_company_id);
        this.employee_department_id = employee_department_id1;
    }

    @Override
    public int getEmployee_department_id() {
        return employee_department_id;
    }

    @Override
    public void setEmployee_department_id(int employee_department_id) {
        this.employee_department_id = employee_department_id;
    }
}
