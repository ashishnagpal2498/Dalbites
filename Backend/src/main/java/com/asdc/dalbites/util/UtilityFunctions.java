package com.asdc.dalbites.util;

import org.springframework.stereotype.Component;

@Component
public class UtilityFunctions {
    public int generateOTP() {
        return (int) Math.floor(100000 + Math.random() * 900000);
    }
    
    public String getFirebaseStorageURL(String imageName) {
    	String url = new String("https://firebasestorage.googleapis.com/v0/b/dalbites-4237e.appspot.com/o/");
    	return url.concat(imageName).concat("?alt=media&token=").concat(imageName);
    }
}
