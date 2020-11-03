package ch.cscf.jeci.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author: Pierre Henry
 */
public class TestEncryption {

    @Test
    public void test() {

        String testStr = "myPassword123";

        byte[] crypted = EncryptionUtil.encrypt(testStr);

        String decrypted = EncryptionUtil.decrypt(crypted);

        assertEquals(decrypted, testStr);
    }

}
