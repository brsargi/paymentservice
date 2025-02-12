package com.brunorota.paymentservice.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EncryptionUtilTest {

    private EncryptionUtil encryptionUtil;

    @BeforeEach
    void setUp() {
        encryptionUtil = new EncryptionUtil();
    }

    @Test
    void testEncrypt_shouldEncryptDataSuccessfully() {

        String data = "TestData";

        String encryptedData = encryptionUtil.encrypt(data);

        assertNotNull(encryptedData);
        assertEquals(data, encryptionUtil.decrypt(encryptedData));
    }

    @Test
    void testDecrypt_shouldDecryptDataSuccessfully() {

        String data = "TestData";
        String encryptedData = encryptionUtil.encrypt(data);

        String decryptedData = encryptionUtil.decrypt(encryptedData);

        assertNotNull(decryptedData);
        assertEquals(data, decryptedData);
    }

    @Test
    void testDecrypt_shouldReturnNullWhenInvalidEncryptedData() {

        String invalidEncryptedData = "InvalidEncryptedData";

        String decryptedData = encryptionUtil.decrypt(invalidEncryptedData);

        assertNull(decryptedData);
    }

    @Test
    void testEncrypt_shouldReturnNullWhenExceptionOccurs() {

        String data = null;

        String encryptedData = encryptionUtil.encrypt(data);

        assertNull(encryptedData);
    }

    @Test
    void testDecrypt_shouldReturnNullWhenExceptionOccurs() {

        String encryptedData = null;

        String decryptedData = encryptionUtil.decrypt(encryptedData);

        assertNull(decryptedData);
    }
}
