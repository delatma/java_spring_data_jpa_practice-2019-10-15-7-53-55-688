package com.tw.apistackbase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.services.CompanyService;
import org.junit.experimental.results.ResultMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyController.class)
@ActiveProfiles(profiles = "test")
class CompanyControllerTest {

    @Autowired
    private CompanyController companyController;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyService companyService;

    @Autowired
    private ObjectMapper objectMapper;

    private Company existingCompany;

    @Test
    public void should_find_company_by_name() throws Exception{
        //given
        Company acompany = new Company("acompany");
        when(companyService.findByNameContaining("a")).thenReturn(Optional.of(acompany));
        //when
        ResultActions result = mvc.perform(get("/companies?name=a"));
        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is("acompany")));
    }

    @Test
    public void should_return_null_when_criteria_does_not_match() throws Exception{
        //given
        //when
        ResultActions result = mvc.perform(get("/companies?name=ccompany"));
        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void should_be_able_to_add_new_company() throws Exception{
        //given
        Company cCompany = new Company("cCompany");
        cCompany.setId(1L);
        when(companyService.save(eq(cCompany))).thenReturn(cCompany);

        //when
        ResultActions result = mvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cCompany))
        );

        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("cCompany")));
    }

    @Test
    public void should_delete_an_existing_company() throws Exception {
//        given
        when(companyService.findById(1)).thenReturn(Optional.of(new Company()));
//        when
        ResultActions result = mvc.perform(delete("/companies/1"));
//        then
    }
}