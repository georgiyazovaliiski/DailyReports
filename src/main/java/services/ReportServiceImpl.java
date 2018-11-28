package services;

import excelserialization.ReportPOJO;
import models.DBModels.Report;
import repositories.implementedrepositories.ReportRepositoryImpl;
import repositories.interfacerepositories.ReportRepository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ReportServiceImpl implements ReportService{
    private ReportRepository reportRepository;

    public ReportServiceImpl(String companyName) throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException {
        reportRepository = new ReportRepositoryImpl(companyName);
    }

    public ReportServiceImpl(String companyName, Connection connection) throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException {
        reportRepository = new ReportRepositoryImpl(companyName);
        this.reportRepository.setConnection(connection);
    }

    @Override
    public Optional<Report> getById(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return reportRepository.get(id);
    }

    @Override
    public Optional<Integer> addReport(Report c) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return reportRepository.insert(c);
    }

    @Override
    public Optional<List<Report>> getAll() throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return reportRepository.get();
    }

    @Override
    public Optional<List<ReportPOJO>> getByPeriod(LocalDate beginDate, LocalDate endDate) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        return reportRepository.getByPeriod(beginDate,endDate);
    }

    @Override
    public void updateReport(Report c) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        reportRepository.update(c);
    }

    @Override
    public void deleteReport(int id) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, NoSuchFieldException, SQLException, IllegalAccessException, InvocationTargetException, IOException {
        reportRepository.delete(id);
    }
}
