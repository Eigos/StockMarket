package com.stockmarket.sproject.application.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@JsonPropertyOrder({"name", "last_name", "balance", "elements"})
public class TransactionHistoryResponse {
    
    String name;

    @JsonProperty(value = "last_name")
    String lastName;

    String balance;

    List<TransactionHistoryElement> elements;
}
