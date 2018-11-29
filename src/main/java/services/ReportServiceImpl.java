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
    public Optional<Report> getById(int id) {
        try{
            return reportRepository.get(id);
        }catch (Exception e){
            System.out.println(e.toString());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> addReport(Report c) {
        try{
            return reportRepository.insert(c);
        }catch (Exception e){
            System.out.println(e.toString());
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Report>> getAll() {
         try{
            return reportRepository.get();
         }catch (Exception e){
             System.out.println(e.toString());
             return Optional.empty();
         }
     }

    @Override
    public Optional<List<ReportPOJO>> getByPeriod(LocalDate beginDate, LocalDate endDate) {
        try{
            return reportRepository.getByPeriod(beginDate,endDate);
        }catch (Exception e){
            System.out.println(e.toString());
            return Optional.empty();
        }
    }

    @Override
    public void updateReport(Report c) {
        try{
            reportRepository.update(c);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    @Override
    public void deleteReport(int id) {
        try{
            reportRepository.delete(id);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
