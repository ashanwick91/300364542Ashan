package com.exam.ashan.controller;

import com.exam.ashan.entity.Salesman;
import com.exam.ashan.repository.SalesmanRepository;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class SalesControllerTest {

    @Mock
    private SalesmanRepository salesmanRepository;

    @Mock
    private View mockView;

    @InjectMocks
    private SalesController salesController;

    private MockMvc mockMvc;

    Salesman salesman;

    @BeforeEach
    void setUp() throws ParseException {
        salesman = new Salesman(1234L, 100.0, new SimpleDateFormat("yyyy/MM/dd").parse("2012/11/11"),
                "Music System", "Jessica Lam");

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(salesController).setSingleView(mockView).build();
    }

    @Test
    void save() throws Exception {
        when(salesmanRepository.save(any(Salesman.class))).thenReturn(salesman);

        mockMvc.perform(MockMvcRequestBuilders.post("/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("id", "1234"),
                                new BasicNameValuePair("amount", "100"),
                                new BasicNameValuePair("dot", "2012-11-11"),
                                new BasicNameValuePair("item", "Music System"),
                                new BasicNameValuePair("name", "Jessica Lam")
                        )))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/index"));
    }

    @Test
    void delete() throws Exception {
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        doNothing().when(salesmanRepository).deleteById(idCaptor.capture());

        mockMvc.perform(MockMvcRequestBuilders.get("/delete").param("id", String.valueOf(1L)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/index"));

        assertEquals(1L, idCaptor.getValue());
        verify(salesmanRepository, times(1)).deleteById(1L);
    }
}