package org.example.lms.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PasswordResetService {

    @Autowired
    private SmsService smsService;

    private static final int OTP_LENGTH = 6; //OTP: One Time Password

    private final Map<String, String> otpCache = new ConcurrentHashMap<>();

    private static final long OTP_EXPIRY_TIME = 5 * 60 * 1000;
    private final Map<String, Long> otpTimestampCache = new ConcurrentHashMap<>();

    public void sendOtpToMobile(String mobile) {
        String otp = generateOtp();
        String message = String.format("Your OTP for password reset is: %s", otp);

        smsService.sendSms(mobile, message);

        storeOtp(mobile, otp);
    }


    private String generateOtp() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(ThreadLocalRandom.current().nextInt(10));
        }
        return otp.toString();
    }


    private void storeOtp(String mobile, String otp) {
        otpCache.put(mobile, otp);
        otpTimestampCache.put(mobile, System.currentTimeMillis());
    }


    public boolean validateOtp(String mobile, String enteredOtp) {
        String storedOtp = otpCache.get(mobile);
        Long timestamp = otpTimestampCache.get(mobile);

        if (storedOtp != null && timestamp != null) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - timestamp <= OTP_EXPIRY_TIME) {
                return storedOtp.equals(enteredOtp);
            } else {
                otpCache.remove(mobile);
                otpTimestampCache.remove(mobile);
            }
        }
        return false;
    }
}
