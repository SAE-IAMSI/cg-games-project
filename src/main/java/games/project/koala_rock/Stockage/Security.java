package games.project.koala_rock.Stockage; //Votre package ici.

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * Cette classe sert à hacher et sécuriser les mots de passe, vous trouverez l'exemple d'utilisation dans PlayerManager.
 */
public class Security {

    private static final String pepper = "vpoLfbyP5KQ22vQ5z2KfJw";

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hmac(String algorithm, String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);
        return toHexString(mac.doFinal(data.getBytes()));
    }

    public static byte[] getSHA(String input, byte[] salt) throws NoSuchAlgorithmException, InvalidKeyException {
        String algo = "HmacSHA256";
        String mdpPepper = hmac(algo, input, pepper);

        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(mdpPepper.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public static boolean checkPassword(String password, byte[] salt, String hash) throws NoSuchAlgorithmException, InvalidKeyException {
        return toHexString(getSHA(password, salt)).equals(hash);
    }
}
