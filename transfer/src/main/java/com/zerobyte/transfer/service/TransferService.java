package com.zerobyte.transfer.service;
import com.zerobyte.transfer.objects.Transfer;
import com.zerobyte.transfer.objects.TransferRequest;
import com.zerobyte.transfer.objects.TransferResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransferService {
    public TransferResponse calculateCheapestRoute(TransferRequest request) {
        List<Transfer> availableTransfers = request.getAvailableTransfers();
        int maxWeight = request.getMaxWeight();
        int numTransfers = availableTransfers.size();

        if (numTransfers == 0 || maxWeight <= 0) {
            return new TransferResponse(new ArrayList<>(), 0, 0);
        }

        int [][] dp = new int[numTransfers + 1][maxWeight + 1];
        boolean [][] selected = new boolean[numTransfers + 1][maxWeight + 1];

        for (int i = 0; i <= numTransfers; i++){
            for (int w = 0; w <= maxWeight; w++){
                if (i == 0 || w == 0){
                    dp[i][w] = 0;
                    continue;
                }
                Transfer currentTransfer = availableTransfers.get(i - 1);
                int weight = currentTransfer.getWeight();
                int cost = currentTransfer.getCost();
                if (weight <= w){
                    int included = cost + dp[i - 1][w - weight];
                    int excluded = dp[i - 1][w];
                    if (included > excluded) {
                        dp[i][w] = included;
                        selected[i][w] = true;
                    } else {
                        dp[i][w] = excluded;
                        selected[i][w] = false;
                    }
                } else {
                    dp[i][w] = dp[i - 1][w];
                    selected[i][w] = false;
                }
            }
        }

        List<Transfer> selectedTransfers = new ArrayList<>();
        int remainingWeight = maxWeight;
        int totalCost = dp[numTransfers][maxWeight];
        int totalWeight = 0;

        for (int i = numTransfers; i > 0 && remainingWeight > 0; i--) {
            if (selected[i][remainingWeight]) {
                Transfer currentTransfer = availableTransfers.get(i - 1);
                selectedTransfers.add(currentTransfer);
                remainingWeight -= currentTransfer.getWeight();
                totalWeight += currentTransfer.getWeight();
            }
        }

        Collections.reverse(selectedTransfers);
        return new TransferResponse(selectedTransfers, totalCost, totalWeight);
    }
}
