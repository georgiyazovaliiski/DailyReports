package models.reportcomponents;

public class Employee {
    private String Name;
    private String City;
    private double Turnover;

    public Employee(String city, String employee, String turnover) {
        this.setCity(city);
        this.setName(employee);
        this.setTurnover(Double.parseDouble(turnover));
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public double getTurnover() {
        return Turnover;
    }

    public void setTurnover(double turnover) {
        Turnover = turnover;
    }
}
