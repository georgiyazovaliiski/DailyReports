package models;

public class CSVDailyReport extends BaseDailyReport{
    private String City;
    private String Employee;
    private double TurnOver;

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getEmployee() {
        return Employee;
    }

    public void setEmployee(String employee) {
        Employee = employee;
    }

    public double getTurnOver() {
        return TurnOver;
    }

    public void setTurnOver(double turnOver) {
        TurnOver = turnOver;
    }
}
