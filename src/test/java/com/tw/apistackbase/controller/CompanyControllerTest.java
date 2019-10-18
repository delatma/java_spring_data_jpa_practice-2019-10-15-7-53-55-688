package com.tw.apistackbase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.services.CompanyService;
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

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
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
    public void should_be_able_to_find_company_by_name() throws Exception {
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
    public void should_return_null_when_company_name_does_not_exist_on_search() throws Exception {
        //given
        //when
        ResultActions result = mvc.perform(get("/companies?name=ccompany"));
        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void should_be_able_to_add_new_company() throws Exception {
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
    public void should_be_able_to_delete_an_existing_company() throws Exception {
//        given
        when(companyService.delete(1)).thenReturn(true);
//        when
        ResultActions result = mvc.perform(delete("/companies/{id}", 1));
//        then
        result.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$", is("Deleted company 1")));
    }

    @Test
    public void should_return_404_if_no_existing_company_on_delete() throws Exception {
//        given
        when(companyService.delete(1)).thenReturn(false);
//        when
        ResultActions result = mvc.perform(delete("/companies/{id}", 1));
//        then
        result.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$", is("Company does not exist for ID 1")));
    }

    @Test
    public void should_be_able_to_update_an_existing_company() throws Exception {
//        given
        Company cCompany = new Company("cCompany");
        when(companyService.findByName("cCompany")).thenReturn(Optional.of(cCompany));
        when(companyService.save(eq(cCompany))).thenReturn(cCompany);
//        when
        ResultActions result = mvc.perform(patch("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cCompany)));
//        then
        result.andExpect(status().isOk());
    }

    @Test
    public void should_return_400_if_no_existing_company_on_update() throws Exception {
//        given
        Company cCompany = new Company("cCompany");
        when(companyService.findByName("dCompany")).thenReturn(Optional.of(cCompany));
        when(companyService.save(eq(cCompany))).thenReturn(cCompany);
//        when
        ResultActions result = mvc.perform(patch("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cCompany)));
//        then
        result.andExpect(status().isBadRequest());
    }

}