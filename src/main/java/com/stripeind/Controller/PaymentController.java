package com.stripeind.Controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Token;
import com.stripe.param.TokenCreateParams;
import com.stripeind.payload.TokenRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@ComponentScan(basePackages = "com.stripeind.Controller")
@NoArgsConstructor
public class PaymentController {

    @Value("${stripe.secret.key}")
    @Autowired
    private String stripeSecretKey; // Add your Stripe secret key here

    @PostMapping("/generate-token")
    public ResponseEntity<Object> generateToken(@RequestBody TokenRequest tokenRequest) {
        Stripe.apiKey = stripeSecretKey;

        try {
            TokenCreateParams tokenParams = TokenCreateParams.builder()
                    .setCard(TokenCreateParams.Card.builder()
                            .setNumber(tokenRequest.getNumber())
                            .setExpMonth(tokenRequest.getExpMonth())
                            .setExpYear(tokenRequest.getExpYear())
                            .setCvc(tokenRequest.getCvc())
                            .build())
                    .build();

            Token token = Token.create(tokenParams);

            return new ResponseEntity<>(token.getId(), HttpStatus.OK);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error generating token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}