package com.example.api2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api2.model.Gift;
import com.example.api2.model.PurchaseDetails;
import com.example.api2.repository.GiftRepository;
import com.example.api2.repository.PurchaseRepository;

@Service
public class GiftService {

    @Autowired
    private GiftRepository giftRepository;

    // Service method to add the gift to the gift_in collection
    public void addGiftIn(Gift gift) {
        giftRepository.save(gift);
    }
    public List<Gift> getAllGiftStock() {
        return giftRepository.findAll();  // Assuming you're using MongoDB, or you can use your custom query here.
    }
    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Map<String, Object>> getEligibleGifts(String customerId, int customerPoints) {
        List<Gift> gifts = giftRepository.findAll();
        List<Map<String, Object>> eligibleGifts = new ArrayList<>();

        for (Gift gift : gifts) {
            Map<String, Object> giftDetails = new HashMap<>();
            giftDetails.put("itemName", gift.getItemName());
            giftDetails.put("pointsNeeded", gift.getPointsNeeded());
            
            if (gift.getStock() <= 0) {
                giftDetails.put("eligibility", "Out of Stock");
            } else if (customerPoints >= gift.getPointsNeeded()) {
                giftDetails.put("eligibility", "Buy");
            } else {
                giftDetails.put("eligibility", "Not Enough Points");
            }

            eligibleGifts.add(giftDetails);
        }

        return eligibleGifts;
    }

    public void purchaseGift(PurchaseDetails purchaseDetails) {
        // Reduce gift stock
        Gift gift = giftRepository.findByName(purchaseDetails.getGiftName());
        if (gift == null || gift.getStock() <= 0) {
            throw new RuntimeException("Gift not available.");
        }

        if (purchaseDetails.getPointsSpent() < gift.getPointsNeeded()) {
            throw new RuntimeException("Insufficient points.");
        }

        gift.setStock(gift.getStock() - 1);
        giftRepository.save(gift);

        // Save purchase details
        purchaseRepository.save(purchaseDetails);
    }
    
}

