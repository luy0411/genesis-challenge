package com.genesis.crypto.wallet.csv;

import com.opencsv.bean.CsvBindByName;

import java.math.BigDecimal;

public class CSVRecord {

    @CsvBindByName
    private String symbol;

    @CsvBindByName
    private BigDecimal quantity;

    @CsvBindByName
    private BigDecimal price;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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
