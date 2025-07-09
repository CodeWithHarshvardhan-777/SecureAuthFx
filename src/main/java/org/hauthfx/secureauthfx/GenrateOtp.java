package org.hauthfx.secureauthfx;

import java.util.Random;

public class GenrateOtp {
    int otp;
    public int otpGenrater(){
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(9);
            stringBuilder.append(num);
        }
        otp = Integer.parseInt(stringBuilder.toString());
        return otp;
    }


    public static void main(String[] args) {
       GenrateOtp genrateOtp = new GenrateOtp();
        System.out.println(genrateOtp.otpGenrater());

    }
}
