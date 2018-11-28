package repositories.interfacerepositories;

import excelserialization.ReportPOJO;
import models.DBModels.Report;
import repositories.baserepositories.BaseRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends BaseRepository<Report> {
    //custom functions
    Optional<List<ReportPOJO>> getByPeriod(LocalDate beginDate, LocalDate endDate) throws SQLException;
}
