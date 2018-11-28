package models.reports;

public class XMLDailyReport extends BaseDailyReport {
    private models.reportcomponents.Cities Cities;

    public models.reportcomponents.Cities getCities() {
        return Cities;
    }

    public void setCities(models.reportcomponents.Cities cities) {
        Cities = cities;
    }
}
