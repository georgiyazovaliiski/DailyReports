package models.DBModels;

import models.reportcomponents.Department;

import java.util.List;

public class City extends  BaseEntity{
    private String Name;

    private List<Department> Departments;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Department> getDepartments() {
        return Departments;
    }

    public void setDepartments(List<Department> departments) {
        Departments = departments;
    }
}
