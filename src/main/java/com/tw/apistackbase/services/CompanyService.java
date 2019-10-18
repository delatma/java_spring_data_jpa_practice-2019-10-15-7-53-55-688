package com.tw.apistackbase.services;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Iterable<Company> findSpecificCompanies(PageRequest pageRequest) {
        return companyRepository.findAll(pageRequest);
    }

    public Optional<Company> findByNameContaining(String name) {
        return companyRepository.findByNameContaining(name);
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Optional<Company> findByName(String name) {
        return companyRepository.findByName(name);
    }

    public Optional<Company> findById(long id) {
        return companyRepository.findById(id);
    }

    public boolean delete(int id) {
        Optional<Company> optionalCompany = findById(id);
        if (optionalCompany.isPresent()) {
            Company existingCompany = optionalCompany.get();
            companyRepository.delete(existingCompany);

            return true;
        }
        return false;
    }
}
