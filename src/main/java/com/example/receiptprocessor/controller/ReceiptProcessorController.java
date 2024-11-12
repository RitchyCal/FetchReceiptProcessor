package com.example.receiptprocessor.controller;

import com.example.receiptprocessor.model.response.PointsDTO;
import com.example.receiptprocessor.model.response.ReceiptDTO;
import com.example.receiptprocessor.model.response.ReceiptRequest;
import com.example.receiptprocessor.service.ReceiptProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptProcessorController {
    private final ReceiptProcessorService receiptProcessorService;

    @Autowired
    public ReceiptProcessorController(ReceiptProcessorService receiptProcessorService) {
        this.receiptProcessorService = receiptProcessorService;
    }

    @PostMapping("/process")
    public ResponseEntity<ReceiptDTO> process(@RequestBody ReceiptRequest receipt){

        ReceiptDTO receiptDto = receiptProcessorService.processReceipt(receipt);
        return  new ResponseEntity<>(receiptDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<PointsDTO> process(@PathVariable String id){
        PointsDTO pointsDTO = receiptProcessorService.getRetailerPoints(id);
        return new ResponseEntity<>(pointsDTO, HttpStatus.OK);
    }
}
