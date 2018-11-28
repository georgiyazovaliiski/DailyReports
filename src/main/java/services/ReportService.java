package services;

import excelserialization.ReportPOJO;
import models.DBModels.Report;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReportService {
    Optional<Report> getById(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    Optional<Integer> addReport(Report r) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    Optional<List<Report>> getAll() throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    Optional<List<ReportPOJO>> getByPeriod(LocalDate beginDate, LocalDate endDate) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    void updateReport(Report r) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
    void deleteReport(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException;
}
