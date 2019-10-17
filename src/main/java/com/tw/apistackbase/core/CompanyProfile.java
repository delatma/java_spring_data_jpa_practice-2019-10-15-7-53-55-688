package com.tw.apistackbase.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class CompanyProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer registeredCapital;
    private String certId;

    public CompanyProfile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(Integer registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyProfile that = (CompanyProfile) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(registeredCapital, that.registeredCapital) &&
                Objects.equals(certId, that.certId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registeredCapital, certId);
    }
}
