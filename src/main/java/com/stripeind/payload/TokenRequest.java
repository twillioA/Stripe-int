package com.stripeind.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

    // Inner class to represent the JSON payload
    public   class TokenRequest {
        private String number;
        private String expMonth;
        private String expYear;
        private String cvc;

        // getters and setters
        // (you can generate these methods using your IDE or write them manually)
    }

