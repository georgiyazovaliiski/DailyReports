package parsers;

import models.reports.BaseDailyReport;

import java.io.IOException;

public abstract class BaseReportParser {
    public abstract BaseDailyReport Report(String FileName) throws IOException;
}
