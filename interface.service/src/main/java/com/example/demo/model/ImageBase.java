package com.example.demo.model;

import java.util.Base64;

public abstract class ImageBase {

    public String getImageBase64() {
        return Base64.getEncoder().encodeToString(getImage());
    }

    public abstract byte[] getImage();
}
