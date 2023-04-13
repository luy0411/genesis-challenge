package com.genesis.crypto.wallet.domain.csv;

import com.opencsv.bean.CsvBindByName;

public class CSVRecord {

    @CsvBindByName
    private String symbol;

    @CsvBindByName
    private Double quantity;

    @CsvBindByName
    private Double price;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CSVRecord{" +
                "symbol='" + symbol + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
