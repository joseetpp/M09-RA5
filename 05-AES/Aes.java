import java.io.IOException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import java.security.*;
import java.util.Arrays;

public class Aes {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";
    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "LaClauSecretaQueVulguis";

    //Metodes per xifrar 
    public static byte[] generaIv(){
        byte[] ivGenerado = new byte[MIDA_IV];
        SecureRandom ramdon = new SecureRandom();
        ramdon.nextBytes(ivGenerado);
        return ivGenerado;
    }
    public static SecretKeySpec generaHash(String password)throws Exception{
        //Convertimos la contraseña a bytes
        byte[] bytesPassword = password.getBytes("UTF-8");
        //Trituramos con 256
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        //Obtenemos el hash (el resultado triturado de exactamente 32 bytes)
        byte[] hash = digest.digest(bytesPassword);
        return new SecretKeySpec(hash, ALGORISME_XIFRAT);

    }
    // Método para recortar solo los primeros 16 bytes
    public static byte[] extreureIv(byte[] missatgeComplet) {
        // Arrays.copyOfRange copia un trozo de un array desde la posición inicial hasta la final 
        return Arrays.copyOfRange(missatgeComplet, 0, MIDA_IV);
    }
    // Método para recortar el resto del mensaje (quitando los primeros 16 bytes del IV)
    public static byte[] extreurePartXifrada(byte[] missatgeComplet) {
        // Copiamos desde el byte 16 (MIDA_IV) hasta el final de la longitud del array
        return Arrays.copyOfRange(missatgeComplet, MIDA_IV, missatgeComplet.length);
    }


    
    public static byte[] xifraAES(String msg, String password)throws Exception{
        //Obtener els bytes de l'String
        byte[] bMsg = msg.getBytes("UTF-8");

        //Genera IvParameterSpec usando nuestro método
        byte[] iv = generaIv();
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        //Genera hash (la llave secreta) usando nuestro método
        SecretKeySpec secretKey = generaHash(password);

        //Encrypt
        Cipher cipher = Cipher.getInstance(FORMAT_AES); 
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec); // Modo cifrado, con la llave y el IV
        byte[] msgXifrat = cipher.doFinal(bMsg); // Cifra los bytes del mensaje

        byte[] msgFinal = new byte[MIDA_IV + msgXifrat.length];
        System.arraycopy(iv, 0, msgFinal, 0, MIDA_IV);
        System.arraycopy(msgXifrat, 0, msgFinal, MIDA_IV, msgXifrat.length);
        return msgFinal;
    }

    public static String desxifraAES(byte[] bIvIMsgXifrat, String password) throws Exception{
        //Extreure l'IV
        byte[] iv = extreureIv(bIvIMsgXifrat);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        //Extreure la part xifrada
        byte[] partXifrada = extreurePartXifrada(bIvIMsgXifrat);
        //Fer Hash de la clau
        SecretKeySpec secretKey = generaHash(password);

        // Configurar el motor de cifrado (Cipher) pero esta vez en modo DECRYPT
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        
        // 5. Descifrar los bytes
        byte[] msgDesxifratBytes = cipher.doFinal(partXifrada);

        // 6. Convertir los bytes descifrados de vuelta a un String de texto legible
        return new String(msgDesxifratBytes, "UTF-8");
    }   
    public static void main(String[] args) {
        String msgs[] = {"Lorem ipsum dicet",
                        "Hola Andrés cómo esté tu cuñado",
                        "Àgora ïlla Ôtto"};

        for(int i=0; i<msgs.length; i++){
            String msg = msgs[i];
            byte[] bXifrats = null;
            String desxifrat ="";
            try{
                bXifrats=xifraAES(msg, CLAU);
                desxifrat = desxifraAES(bXifrats, CLAU);
            }catch(Exception e){
                System.err.println("Error de xifrat: " + e.getLocalizedMessage());
            }
            System.out.println("---------------------");
            System.out.println("MSG: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("Dec: " + desxifrat);
        }
    }
}