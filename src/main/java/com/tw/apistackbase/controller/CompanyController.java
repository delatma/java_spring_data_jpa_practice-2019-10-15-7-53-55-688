package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repositories.CompanyRepository;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping(produces = {"application/json"})
    public Iterable<Company> list() {
        return companyRepository.findAll();
    }

    @PostMapping(produces = {"application/json"})
    public Company add(@RequestBody Company company) {
        return companyRepository.save(company);

//        We don't say companyRepository.save(company); return
//        Since the returned company does not have a value for ID
//
//        companyRepository.save(company); modifies company to set a value for ID
//        according to @GeneratedValue
    }

    @PatchMapping(produces = {"application/json"})
    public ResponseEntity<String> updateCompany(@RequestBody Company company) {
        Optional<Company> optionalCompany = companyRepository.findById(company.getId());

        if (optionalCompany.isPresent()) {
            Company existingCompany = optionalCompany.get();
            existingCompany.setName(company.getName());
            existingCompany.setEmployees(company.getEmployees());
            existingCompany.setProfile(company.getProfile());

            companyRepository.save(existingCompany);

            return ResponseEntity.ok("Updated company " + existingCompany.getId());
        } else {
            return ResponseEntity.badRequest().body("Company does not exist for ID " + company.getId());
        }
    }

    @DeleteMapping(produces = {"application/json"})
    @RequestMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            Company existingCompany = optionalCompany.get();
            companyRepository.delete(existingCompany);
            return ResponseEntity.ok("Deleted company " + id);
        } else {
            return ResponseEntity.badRequest().body("Company does not exist for ID " + id);
        }
    }
}
