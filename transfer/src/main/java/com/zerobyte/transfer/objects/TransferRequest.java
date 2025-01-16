package com.zerobyte.transfer.objects;
import com.zerobyte.transfer.objects.Transfer;

import java.util.ArrayList;
import java.util.List;

public class TransferRequest {
    private List<Transfer> availableTransfers;
    private int maxWeight;

    public TransferRequest(List<Transfer> availableTransfers, int maxWeight){
        this.availableTransfers = availableTransfers;
        this.maxWeight = maxWeight;
    }

    public void setAvailableTransfers(List<Transfer> availableTransfers){
        this.availableTransfers = availableTransfers;
    }

    public void setMaxWeight(int maxWeight){
        this.maxWeight = maxWeight;
    }

    public List<Transfer> getAvailableTransfers(){
        return new ArrayList<>(this.availableTransfers);
    }

    public int getMaxWeight(){
        return this.maxWeight;
    }
}