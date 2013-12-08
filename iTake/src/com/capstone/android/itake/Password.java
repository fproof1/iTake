/*The code for this password class, including the iterative salted hash
 * was created by Mr.Martin Konicek and is used with implied permission.  The author has been contacted to 
 * obtain explicit permission to use this code in this project.
 */

package com.capstone.android.itake;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;




public class Password {
	
	private static final int hashIters = 65536;
	private static final int saltSize = 64;
	private static final int keySize = 512;
	private static String m_password;
	
	public Password() {
		
	}
	public Password(String pass) {
		m_password = pass;
	}
	
	private String getPString() {
		return m_password;
	}
	
	
	public static String getSaltedHash(Password userpass) throws Exception {
		byte[] salt;
			salt = SecureRandom.getInstance("SHA1PRNG", "SUN").generateSeed(saltSize);
			String mypass = userpass.getPString();
		return Base64.encodeBase64String(salt) + "@" + hash(mypass, salt);
		
	}
	
	public static boolean check(Password entered, String hashed) throws Exception {
		String[] hashCombo = hashed.split("@");
		String enteredP = entered.getPString();
		if (hashCombo.length != 2) {
			throw new IllegalArgumentException("Improper hash detected");
		}
		String hashOfInput = hash(enteredP, Base64.decodeBase64(hashCombo[0]));
		return hashOfInput.equals(hashCombo[1]);
	}
	
	
	private static String hash(String password, byte[] salt) throws Exception {
		
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey key = f.generateSecret(new PBEKeySpec (
				password.toCharArray(), salt, hashIters, keySize)
		);
		return Base64.encodeBase64String(key.getEncoded());
	}
	


}

