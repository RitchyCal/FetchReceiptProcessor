package com.example.receiptprocessor.repo;

import com.example.receiptprocessor.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReceiptProcessorRepository extends JpaRepository<Receipt, String> {
    @Query(value = "select r from receipts where r.id = :id",nativeQuery = true)
    Optional<Receipt> findByUserId (@Param("id") String id);
}
