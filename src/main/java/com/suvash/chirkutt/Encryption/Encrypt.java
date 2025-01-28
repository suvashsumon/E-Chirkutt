package com.suvash.chirkutt.Encryption;

import jakarta.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class Encrypt implements AttributeConverter<String,String> {
    @Autowired
    EncryptionUtil encryptionUtil;

    @Override
    public String convertToDatabaseColumn(String s) {
        if(s==null) return null;
        return encryptionUtil.encrypt(s);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        if(s==null) return null;
        return encryptionUtil.decrypt(s);
    }
}