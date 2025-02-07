package com.zerobyte.transfer.controller;

import com.zerobyte.transfer.objects.TransferRequest;
import com.zerobyte.transfer.objects.TransferResponse;
import com.zerobyte.transfer.service.TransferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private final TransferService transferService;
    private List<Transfer> unusedTransfers;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
        this.unusedTransfers = new ArrayList<Transfer>();
    }

    @PostMapping("/calculate")
    public TransferResponse calculateRoute(@RequestBody TransferRequest request) {
        return transferService.calculateCheapestRoute(request, this.unusedTransfers);
    }
}