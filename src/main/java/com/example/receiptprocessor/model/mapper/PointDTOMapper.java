package com.example.receiptprocessor.model.mapper;

import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.model.response.PointsDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PointDTOMapper implements Function<Receipt, PointsDTO> {
    @Override
    public PointsDTO apply(Receipt receipt){
        return new PointsDTO(
                receipt.getPoints()
        );
    }
}
