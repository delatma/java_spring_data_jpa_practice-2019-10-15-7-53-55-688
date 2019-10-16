package com.tw.apistackbase.repositories;

import com.tw.apistackbase.core.Company;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
=======
import org.hibernate.query.criteria.internal.compile.CriteriaQueryTypeQueryAdapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("Select c from Company c where c.name = :name")
    Optional<Company> findByName(@Param("name") String name);
>>>>>>> b1735056ce6713ae4c13a6d141f8535d2549b767
}
