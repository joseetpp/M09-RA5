package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class XifradorAES implements Xifrador {
    private static final String ALGORISME = "AES";
    private static final String TRANSFORMACIO = "AES/CBC/PKCS5Padding";
    private static final int MIDA_BLOC = 16;

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau == null) {
            throw new ClauNoSuportada("Clau d'AES no pot ser null");
        }
        try {
            byte[] iv = new byte[MIDA_BLOC];
            new SecureRandom().nextBytes(iv);

            Cipher cipher = Cipher.getInstance(TRANSFORMACIO);
            cipher.init(Cipher.ENCRYPT_MODE, generaClau(clau), new IvParameterSpec(iv));
            byte[] xifrat = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));

            // Resultat = IV (16 bytes) + text xifrat
            byte[] resultat = new byte[iv.length + xifrat.length];
            System.arraycopy(iv, 0, resultat, 0, iv.length);
            System.arraycopy(xifrat, 0, resultat, iv.length, xifrat.length);
            return new TextXifrat(resultat);
        } catch (Exception e) {
            System.err.println("Error xifrant amb AES: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau == null) {
            throw new ClauNoSuportada("Clau d'AES no pot ser null");
        }
        try {
            byte[] dades = xifrat.getBytes();
            byte[] iv = Arrays.copyOfRange(dades, 0, MIDA_BLOC);
            byte[] contingut = Arrays.copyOfRange(dades, MIDA_BLOC, dades.length);

            Cipher cipher = Cipher.getInstance(TRANSFORMACIO);
            cipher.init(Cipher.DECRYPT_MODE, generaClau(clau), new IvParameterSpec(iv));
            return new String(cipher.doFinal(contingut), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println("Error desxifrant amb AES: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    // Deriva una clau AES de 128 bits a partir del text de la clau (qualsevol longitud)
    private SecretKeySpec generaClau(String clau) throws Exception {
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(clau.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(Arrays.copyOf(hash, MIDA_BLOC), ALGORISME);
    }
}
