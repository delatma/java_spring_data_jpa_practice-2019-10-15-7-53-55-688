package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {
//    @Autowired
//    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/all", produces = {"application/json"})
    public Iterable<Company> listMultipleCompanies(@RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "5") Integer pageSize) {
        if(page == null || pageSize==null){
            return companyService.findAll();
        }

        Sort.Order orderByName = new Sort.Order(Sort.Direction.ASC, "name").ignoreCase();
        return companyService.findSpecificCompanies(PageRequest.of(page, pageSize, Sort.by(orderByName)));
    }

    @GetMapping(produces = {"application/json"})
    public Company findCompany(@RequestParam(required = false) String name){
        if (name == null) {
            return null;
        }
        Optional<Company> company = companyService.findByNameContaining(name);

        return company.orElse(null);
    }

    @PostMapping(produces = {"application/json"})
    public Company add(@RequestBody Company company) {
        return companyService.save(company);

//        We don't say companyRepository.save(company); return
//        Since the returned company does not have a value for ID
//
//        companyRepository.save(company); modifies company to set a value for ID
//        according to @GeneratedValue
    }

    @PatchMapping(produces = {"application/json"})
    public ResponseEntity<String> updateCompany(@RequestBody Company company) {
        Optional<Company> optionalCompany = companyService.findByName(company.getName());

        if (optionalCompany.isPresent()) {
            Company existingCompany = optionalCompany.get();
            existingCompany.setName(company.getName());
            existingCompany.setEmployees(company.getEmployees());
            existingCompany.setProfile(company.getProfile());

            companyService.save(existingCompany);

            return new ResponseEntity<>("Updated company " + existingCompany.getName(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Company " + company.getName()+ " does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(produces = {"application/json"})
    @RequestMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable long id) {
        Optional<Company> optionalCompany = companyService.findById(id);
        if (optionalCompany.isPresent()) {
            Company existingCompany = optionalCompany.get();
            companyService.delete(existingCompany);

            return new ResponseEntity<>("Deleted company " + id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Company does not exist for ID " + id, HttpStatus.BAD_REQUEST);
        }
    }
}