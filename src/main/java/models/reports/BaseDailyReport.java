package models.reports;

import models.reportcomponents.Company;

import java.time.LocalDate;
import java.util.Date;

public abstract class BaseDailyReport {
    private models.reportcomponents.Company Company;
    private LocalDate Date;

    public models.reportcomponents.Company getCompany() {
        return Company;
    }

    public void setCompany(models.reportcomponents.Company company) {
        Company = company;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }
}
