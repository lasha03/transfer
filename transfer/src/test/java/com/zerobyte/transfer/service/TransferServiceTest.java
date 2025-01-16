package com.zerobyte.transfer.service;

import com.zerobyte.transfer.objects.Transfer;
import com.zerobyte.transfer.objects.TransferRequest;
import com.zerobyte.transfer.objects.TransferResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransferServiceTest {

    private TransferService transferService;

    @BeforeEach
    void setUp() {
        transferService = new TransferService();
    }

    @Test
    void whenValidInput_thenReturnsOptimalRoute() {
        // Given
        List<Transfer> transfers = Arrays.asList(
                new Transfer(5, 10),
                new Transfer(10, 20),
                new Transfer(3, 5),
                new Transfer(8, 15)
        );
        TransferRequest request = new TransferRequest(transfers, 15);

        // When
        TransferResponse response = transferService.calculateCheapestRoute(request);

        // Then
        assertEquals(30, response.getTotalCost());
        assertEquals(15, response.getTotalWeight());
        assertEquals(2, response.getSelectedTransfers().size());

        // Verify selected transfers
        List<Transfer> selectedTransfers = response.getSelectedTransfers();
        assertTrue(selectedTransfers.stream().anyMatch(t -> t.getWeight() == 5 && t.getCost() == 10));
        assertTrue(selectedTransfers.stream().anyMatch(t -> t.getWeight() == 10 && t.getCost() == 20));
    }

    @Test
    void whenEmptyTransferList_thenReturnsEmptyResponse() {
        // Given
        TransferRequest request = new TransferRequest(new ArrayList<>(), 15);

        // When
        TransferResponse response = transferService.calculateCheapestRoute(request);

        // Then
        assertTrue(response.getSelectedTransfers().isEmpty());
        assertEquals(0, response.getTotalCost());
        assertEquals(0, response.getTotalWeight());
    }

    @Test
    void whenZeroMaxWeight_thenReturnsEmptyResponse() {
        // Given
        List<Transfer> transfers = Arrays.asList(
                new Transfer(5, 10),
                new Transfer(10, 20)
        );
        TransferRequest request = new TransferRequest(transfers, 0);

        // When
        TransferResponse response = transferService.calculateCheapestRoute(request);

        // Then
        assertTrue(response.getSelectedTransfers().isEmpty());
        assertEquals(0, response.getTotalCost());
        assertEquals(0, response.getTotalWeight());
    }

    @Test
    void whenAllTransfersExceedMaxWeight_thenReturnsEmptyResponse() {
        // Given
        List<Transfer> transfers = Arrays.asList(
                new Transfer(10, 20),
                new Transfer(15, 30)
        );
        TransferRequest request = new TransferRequest(transfers, 5);

        // When
        TransferResponse response = transferService.calculateCheapestRoute(request);

        // Then
        assertTrue(response.getSelectedTransfers().isEmpty());
        assertEquals(0, response.getTotalCost());
        assertEquals(0, response.getTotalWeight());
    }

    @Test
    void whenSingleTransferFits_thenReturnsThatTransfer() {
        // Given
        List<Transfer> transfers = Arrays.asList(
                new Transfer(5, 10),
                new Transfer(10, 20)
        );
        TransferRequest request = new TransferRequest(transfers, 6);

        // When
        TransferResponse response = transferService.calculateCheapestRoute(request);

        // Then
        assertEquals(1, response.getSelectedTransfers().size());
        assertEquals(10, response.getTotalCost());
        assertEquals(5, response.getTotalWeight());
    }

    @Test
    void whenMultipleValidCombinations_thenReturnsHighestCost() {
        // Given
        List<Transfer> transfers = Arrays.asList(
                new Transfer(5, 10),
                new Transfer(5, 8),
                new Transfer(5, 7)
        );
        TransferRequest request = new TransferRequest(transfers, 10);

        // When
        TransferResponse response = transferService.calculateCheapestRoute(request);

        // Then
        assertEquals(18, response.getTotalCost());
        assertEquals(10, response.getTotalWeight());
        assertEquals(2, response.getSelectedTransfers().size());
    }
}