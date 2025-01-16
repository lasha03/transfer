package com.zerobyte.transfer.objects;

import java.util.ArrayList;
import java.util.List;

public class TransferResponse {
    private List<Transfer> selectedTransfers;
    private int totalCost;
    private int totalWeight;

    public TransferResponse(List<Transfer> selectedTransfers, int totalCost, int totalWeight){
        this.selectedTransfers = selectedTransfers;
        this.totalCost = totalCost;
        this.totalWeight = totalWeight;
    }

    public void setSelectedTransfers(List<Transfer> selectedTransfers){
        this.selectedTransfers = selectedTransfers;
    }

    public void setTotalCost(int totalCost){
        this.totalCost = totalCost;
    }

    public void setTotalWeight(int totalWeight){
        this.totalWeight = totalWeight;
    }

    public List<Transfer> getSelectedTransfers(){
        return new ArrayList<>(this.selectedTransfers);
    }

    public int getTotalWeight(){
        return this.totalWeight;
    }

    public int getTotalCost(){
        return this.totalCost;
    }

}