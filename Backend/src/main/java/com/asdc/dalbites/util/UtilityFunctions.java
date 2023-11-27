package com.asdc.dalbites.util;

import org.springframework.stereotype.Component;


/**
 * Utility functions for common operations.
 */
@Component
public class UtilityFunctions {

    /**
     * Generates a random six-digit OTP (One-Time Password).
     *
     * @return A randomly generated six-digit OTP.
     */
    public int generateOTP() {
        return (int) Math.floor(Constants.RANDOM_NUMBER_START + Math.random() * Constants.RANDOM_NUMBER_END);
    }

    /**
     * Constructs the Firebase Storage URL for the given image name.
     *
     * @param imageName The name of the image.
     * @return The Firebase Storage URL for the image.
     */
    public String getFirebaseStorageURL(String imageName) {
    	String url = new String("https://firebasestorage.googleapis.com/v0/b/dalbites-4237e.appspot.com/o/");
    	return url.concat(imageName).concat("?alt=media&token=").concat(imageName);
    }
}
