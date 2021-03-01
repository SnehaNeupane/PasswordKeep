package com.example.pwdkeeper;


import android.util.Base64;

public class Encryption {

    public String encodeData(String data) {
        byte [] byte_data = data.getBytes();
        String encoded = Base64.encodeToString(byte_data,Base64.DEFAULT);
        return encoded;
    }


}


