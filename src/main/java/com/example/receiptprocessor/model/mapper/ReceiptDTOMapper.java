package com.example.receiptprocessor.model.mapper;

import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.model.response.ReceiptDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ReceiptDTOMapper implements Function<Receipt, ReceiptDTO> {
    @Override
    public ReceiptDTO apply(Receipt receipt){
        return new ReceiptDTO(
                receipt.getId()
        );
    }
}
