package com.example.receiptprocessor.service;

import com.example.receiptprocessor.model.Product;
import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.model.mapper.PointDTOMapper;
import com.example.receiptprocessor.model.mapper.ReceiptDTOMapper;
import com.example.receiptprocessor.model.response.PointsDTO;
import com.example.receiptprocessor.model.response.ReceiptDTO;
import com.example.receiptprocessor.model.response.ReceiptRequest;
import com.example.receiptprocessor.repo.ReceiptProcessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class ReceiptProcessorService {
    private final  ReceiptProcessorRepository receiptProcessorRepository;
    private final ReceiptDTOMapper receiptDTOMapper;
    private final PointDTOMapper pointDTOMapper;

    @Autowired
    public ReceiptProcessorService(ReceiptProcessorRepository receiptProcessorRepository, ReceiptDTOMapper receiptDTOMapper, PointDTOMapper pointDTOMapper) {
        this.receiptProcessorRepository = receiptProcessorRepository;
        this.receiptDTOMapper = receiptDTOMapper;
        this.pointDTOMapper = pointDTOMapper;
    }

    public ReceiptDTO processReceipt(ReceiptRequest receipt){
        /*One point for every alphanumeric character in the retailer name.
        50 points if the total is a round dollar amount with no cents.
        25 points if the total is a multiple of 0.25.
        5 points for every two items on the receipt.
        If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer. The result is the number of points earned.
        6 points if the day in the purchase date is odd.
        10 points if the time of purchase is after 2:00pm and before 4:00pm.*/

        Receipt newReceipt = new Receipt();
        newReceipt.setRetailer(receipt.getRetailer());
        newReceipt.setPoints(calculatePoints(receipt));


        return receiptDTOMapper.apply(receiptProcessorRepository.save(newReceipt));
    }

    private long calculatePoints(ReceiptRequest request){
        long totalPoints = 0;
        totalPoints +=  howManyAlphaNumericChar(request.getRetailer());
        totalPoints += calculateDescriptionPoints(request.getItems());
        totalPoints += calculatePointsFromDateAndTime(request.getPurchaseDate(), request.getPurchaseTime());
        if(request.getTotal() % 1 == 0){
            totalPoints += 50;
        }
        if(request.getTotal() % 0.25 == 0){
            totalPoints += 25;
        }
        totalPoints += (request.getItems().size() / 2) * 5L;

        return totalPoints;
    }

    private long howManyAlphaNumericChar(String name){
        long total = 0;
        for (int i = 0; i < name.length(); i++) {
            if(Character.isLetterOrDigit(name.charAt(i))) {
                total++;
            }
        }
        return total;
    }

    private long calculateDescriptionPoints(List<Product> items){
        long points = 0;
        for(Product product : items){
            String description = product.getShortDescription().trim();
            if(description.length() % 3 == 0){
                points += (int) Math.ceil(product.getPrice() * 0.2);
            }
        }

        return points;
    }

    private long calculatePointsFromDateAndTime(Date date, LocalTime time){
        LocalTime start = LocalTime.of(14,0);
        LocalTime end = LocalTime.of(16,0);
        long points =0;
        if(date.getDay() % 2 == 1){
            points += 6;
        }

        if(time.isBefore(end) && time.isAfter(start)){
            points +=10;
        }

        return points;
    }

    public PointsDTO getRetailerPoints(String id){
        return pointDTOMapper.apply(receiptProcessorRepository.getById(id));
    }
}
