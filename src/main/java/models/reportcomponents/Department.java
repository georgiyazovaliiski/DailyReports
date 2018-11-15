package models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Department {
    @XmlAttribute(name = "name")
    private String DepartmentName;
    @XmlElement(name = "employee")
    private String FullName;
    @XmlElement(name = "turnover")
    private double TurnOver;

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public double getTurnOver() {
        return TurnOver;
    }

    public void setTurnOver(double turnOver) {
        TurnOver = turnOver;
    }
}
