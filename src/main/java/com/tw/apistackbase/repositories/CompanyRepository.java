package com.tw.apistackbase.repositories;

import com.tw.apistackbase.core.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("Select c from Company c where c.name = :name")
    Optional<Company> findByName(@Param("name") String name);

    Optional<Company> findByNameContaining(String name);
}