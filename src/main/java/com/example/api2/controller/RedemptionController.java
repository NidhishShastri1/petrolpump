package com.example.api2.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.api2.model.PurchaseDetails;
import com.example.api2.service.GiftService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/redemption")
public class RedemptionController {

    @Autowired
    private GiftService giftService;

    // API to fetch eligible gifts
    @GetMapping("/eligible-gifts")
    public ResponseEntity<List<Map<String, Object>>> getEligibleGifts(
            @RequestParam String customerId, 
            @RequestParam int customerPoints) {
        try {
            List<Map<String, Object>> eligibleGifts = giftService.getEligibleGifts(customerId, customerPoints);
            return ResponseEntity.ok(eligibleGifts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // API to process gift purchase
    @PostMapping("/buy")
    public ResponseEntity<String> purchaseGift(@RequestBody PurchaseDetails purchaseDetails) {
        try {
            giftService.purchaseGift(purchaseDetails);
            return ResponseEntity.ok("Gift purchased successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}



