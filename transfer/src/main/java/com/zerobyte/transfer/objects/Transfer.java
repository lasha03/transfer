package com.zerobyte.transfer.objects;

public class Transfer {
    private int weight;
    private int cost;

    public Transfer(int weight, int cost){
        this.weight = weight;
        this.cost = cost;
    }


    public void setWeight(int weight){
        this.weight = weight;
    }

    public void setCost(int cost){
        this.cost = cost;
    }

    public int getWeight(){
        return this.weight;
    }

    public int getCost(){
        return this.cost;
    }
}