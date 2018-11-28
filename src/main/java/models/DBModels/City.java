package models.DBModels;

import annotation.Table;
import models.reportcomponents.Department;

import java.util.List;

@Table(name = "cities")
public class City extends BaseEntity{
    private String city_name;

    //private List<Department> Departments;

    public City(int id) {
        super(id);
    }

    public String getName() {
        return city_name;
    }

    public void setName(String name) {
        city_name = name;
    }

    /*public List<Department> getDepartments() {
        return Departments;
    }
*/
  /*  public void setDepartments(List<Department> departments) {
        Departments = departments;
    }
*/
    public City(String name) {
        city_name = name;
        //Departments = departments;
    }

    public City(int id, String name/*,List<Department> departments*/) {
        super(id);
        city_name = name;
        //Departments = departments;
    }

    public City(){

    }
}
