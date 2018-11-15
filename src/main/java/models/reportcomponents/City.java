package models;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class City {
    @XmlAttribute(name = "name")
    private String Name;

    @XmlElementWrapper(name = "departments")
    @XmlElement(name = "department")
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
