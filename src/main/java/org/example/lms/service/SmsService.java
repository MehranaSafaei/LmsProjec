package org.example.lms.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {


    public void sendSms(String mobile, String message) {
        System.out.println("Sending SMS to " + mobile + ": " + message);

    }

}
