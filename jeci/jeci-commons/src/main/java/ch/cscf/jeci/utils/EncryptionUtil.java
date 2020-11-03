package ch.cscf.jeci.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {
	
	private static final String SYMETRICAL_ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding";
	private static final String AES_INITIALIZATION_VECTOR = "InitializationVe";
	private static final String AES_SECRET_KEY = "1dGf9H29ge02Yw0I";
	private static final SecretKeySpec KEYSPEC = new SecretKeySpec(AES_SECRET_KEY.getBytes(), "AES");

    private static final String HASH_ALGORITHM = "SHA-1";
    private static MessageDigest messageDigest;

    static {
        try{
            messageDigest = MessageDigest.getInstance("SHA-1");
        }catch(NoSuchAlgorithmException e){
            throw new IllegalArgumentException("Unsupported hashing algorithm : "+HASH_ALGORITHM, e);
        }

    }

	public static byte[] encrypt (String s){
		try{
			return encrypt(s.getBytes("UTF-8"));
		}catch(UnsupportedEncodingException e){
			throw new RuntimeException("Unsupported encoding", e);
		}
	}
	
	public static byte[] encrypt(byte[] s) {
		
		Cipher cipher = getCypher(KEYSPEC, Cipher.ENCRYPT_MODE);
		
		try{
			return cipher.doFinal(s);
		}catch(BadPaddingException e){
			throw new RuntimeException("Bad padding.", e);
		}catch(IllegalBlockSizeException e){
			throw new RuntimeException("Illegal block size", e);
		}
	}
	
	public static String decrypt(byte[] b){
		
		Cipher cipher = getCypher(KEYSPEC, Cipher.DECRYPT_MODE);

		try{
			return new String(cipher.doFinal(b), "UTF-8");
		}catch(UnsupportedEncodingException e){
			throw new RuntimeException("Unsupported encoding", e);
		}catch(BadPaddingException e){
			throw new RuntimeException("Bad padding.", e);
		}catch(IllegalBlockSizeException e){
			throw new RuntimeException("Illegal block size", e);
		}
	}
	
	public static Cipher getCypher(SecretKeySpec keySpec, int mode) {
		// Get a cipher object.
		Cipher cipher;
		IvParameterSpec ivSpec = new IvParameterSpec(AES_INITIALIZATION_VECTOR.getBytes());
		try {
			cipher = Cipher.getInstance(SYMETRICAL_ENCRYPTION_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Invalid Algorithm.", e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException("Invalid Padding.", e);
		}
		try {
			cipher.init(mode, keySpec, ivSpec);
		} catch (InvalidKeyException e) {
			throw new RuntimeException("Invalid Key.", e);
		} catch (InvalidAlgorithmParameterException e) {
			throw new RuntimeException("Invalid Algorithm.", e);
		}
		return cipher;
	}

    /**
     * Generates the Hex Hash Digest for a String
     *
     * @param aString a <code>String</code> whose SHA-1 value is calculated
     * @return a <code>String</code> SHA-1 hash diggest in HEX form
     */
    public static String getHash(String aString){

        if(aString == null){
            throw new IllegalArgumentException("aString must not be null");
        }
        byte[] plainText = null;
        byte[] digest;
        String hexString=null;

        try {
            plainText = aString.getBytes("UTF8");
            messageDigest.update(plainText);
            digest = messageDigest.digest();
            hexString = DatatypeConverter.printHexBinary(digest);
        }
        catch(UnsupportedEncodingException ex) {
            throw new RuntimeException("Unsupported Encoding Exception !", ex);
        }
        return hexString;
    }

}
