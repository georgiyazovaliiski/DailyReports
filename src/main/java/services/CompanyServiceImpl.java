package services;

import models.DBModels.Company;
import repositories.interfacerepositories.CompanyRepository;
import repositories.implementedrepositories.CompanyRepositoryImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CompanyServiceImpl implements CompanyService{
    private CompanyRepository companyRepository;

    public CompanyServiceImpl() {
        try {
            companyRepository = new CompanyRepositoryImpl();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public CompanyServiceImpl(Connection connection) {
        try {
            companyRepository = new CompanyRepositoryImpl();
            companyRepository.setConnection(connection);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }


    @Override
    public Optional<Company> getById(int id) {
        try{
            return companyRepository.get(id);
        }
        catch (Exception e){
            System.out.println(e.toString());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> addCompany(Company c) {
        try {
            return companyRepository.insert(c);
        }catch (Exception e) {
            System.out.println(e.toString());
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Company>> getAll() {
        try {
            return companyRepository.get();
        }catch (Exception e) {
            System.out.println(e.toString());
            return Optional.empty();
        }
    }

    @Override
    public void updateCompany(Company c) {
        try{
        companyRepository.update(c);
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void deleteCompany(int id) {
        try{
        companyRepository.delete(id);
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public Optional<Company> getByCompanyPrefix(String companyPrefix){
        try{
            return companyRepository.getByPrefix(companyPrefix);
        }catch (Exception e) {
            System.out.println(e.toString());
            return Optional.empty();
        }
    }
}
