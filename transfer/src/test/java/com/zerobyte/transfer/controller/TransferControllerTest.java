package com.zerobyte.transfer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobyte.transfer.objects.Transfer;
import com.zerobyte.transfer.objects.TransferRequest;
import com.zerobyte.transfer.objects.TransferResponse;
import com.zerobyte.transfer.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TransferControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private TransferService transferService;

    @InjectMocks
    private TransferController transferController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transferController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void whenValidRequest_thenReturnsSuccessfulResponse() throws Exception {
        List<Transfer> transfers = Arrays.asList(
                new Transfer(5, 10),
                new Transfer(10, 20)
        );
        TransferRequest request = new TransferRequest(transfers, 15);
        TransferResponse expectedResponse = new TransferResponse(transfers, 30, 15);

        when(transferService.calculateCheapestRoute(any(TransferRequest.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/api/transfers/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(30))
                .andExpect(jsonPath("$.totalWeight").value(15))
                .andExpect(jsonPath("$.selectedTransfers").isArray())
                .andExpect(jsonPath("$.selectedTransfers.length()").value(2));
    }

    @Test
    void whenEmptyTransferList_thenReturnsEmptyResponse() throws Exception {
        TransferRequest request = new TransferRequest(new ArrayList<>(), 15);
        TransferResponse expectedResponse = new TransferResponse(new ArrayList<>(), 0, 0);

        when(transferService.calculateCheapestRoute(any(TransferRequest.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/api/transfers/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(0))
                .andExpect(jsonPath("$.totalWeight").value(0))
                .andExpect(jsonPath("$.selectedTransfers").isEmpty());
    }

    @Test
    void whenInvalidJson_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/transfers/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"availableTransfers\": [{}], \"maxWeight\": \"not-a-number\"}"))  // invalid data types
                .andExpect(status().isBadRequest());
    }
}