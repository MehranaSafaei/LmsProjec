package org.example.lms.serviceImp;

import org.springframework.stereotype.Service;

@Service
public class SmsService {


    public void sendSms(String number, String message) {
        System.out.println("Sending SMS to " + number + ": " + message);

    }

}
