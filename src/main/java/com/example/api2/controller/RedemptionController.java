package com.example.api2.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.api2.model.PurchaseDetails;
import com.example.api2.model.Redemption;
import com.example.api2.service.GiftService;
import com.example.api2.service.RedemptionService;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:3000")
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

 
        @Autowired
        private GiftService GiftService;


        @PostMapping("/buy")
        public ResponseEntity<?> buyGift(@RequestParam("pointsNeeded") int pointsNeeded,
                                         @RequestBody Redemption redemption) {
            try {
                // Pass the necessary parameters to the service
                RedemptionService redemptionService = new RedemptionService();
                redemptionService.redeemGift(redemption.getCustomerId(), redemption.getGiftName(), pointsNeeded);
                
                return ResponseEntity.ok("Gift redeemed successfully.");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error: " + e.getMessage());
            }
        }

}





