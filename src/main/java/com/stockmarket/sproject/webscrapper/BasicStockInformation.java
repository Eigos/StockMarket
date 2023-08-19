package com.stockmarket.sproject.webscrapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BasicStockInformation {
    
    private String symbol;
    private String description;
    private double value;
    
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        long temp;
        temp = Double.doubleToLongBits(value);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BasicStockInformation other = (BasicStockInformation) obj;
        if (symbol == null) {
            if (other.symbol != null)
                return false;
        } else if (!symbol.equals(other.symbol))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "BasicStockInformation [symbol=" + symbol + ", description=" + description + ", value=" + value + "]";
    }

}
