import java.security.MessageDigest;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    public int npass = 0; 
    //Aplica el algoritmo de cifrado SHA-512 utilizando la contraseña y el salt.
    public String getSHA512AmbSalt(String pw, String salt) {
        try {
            //instancia del algoritmo SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            //Añadimos el salt al cifrado (convirtiéndolo a bytes)
            md.update(salt.getBytes());
            //Aplicamos el hash a la contraseña.
            // digest() calcula el hash final
            byte[] bytes = md.digest(pw.getBytes());
            //Convertimos el byte[] resultante en un String
            HexFormat hex = HexFormat.of();
            String hash = hex.formatHex(bytes);

            return hash;
        } catch (Exception e) {
            // Capturamos la excepción por si el algoritmo "SHA-512" no está disponible
            e.printStackTrace();
            return null;
        }
    }

    //Aplica el algoritmo de cifrado PBKDF2 utilizando la contraseña y el salt.
    // Al igual que en SHA-512, debe devolver el hash convertido a String hexadecimal
    public String getPBKDF2AmbSalt(String pw, String salt) {
        // Implementar cifrado PBKDF2
try {
        // 1. Configuramos los parámetros (iteraciones y longitud de la clave en bits)
        // Usamos 128 bits porque el resultado esperado en el PDF tiene 32 caracteres hexadecimales[cite: 3]
        int iteraciones = 10000; 
        int longitudClaveBits = 128; 
        
        // Creamos el objeto de especificaciones con la contraseña, el salt, las iteraciones y la longitud
        PBEKeySpec spec = new PBEKeySpec(pw.toCharArray(), salt.getBytes(), iteraciones, longitudClaveBits);
        
        // Obtenemos la instancia del algoritmo PBKDF2 (puedes usar HmacSHA1, HmacSHA256, etc.)
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        
        // Generamos la clave secreta y obtenemos sus bytes
        byte[] bytes = skf.generateSecret(spec).getEncoded();
        
        // Convertimos el byte[] resultante en un String hexadecimal, usando el código aportado[cite: 1]
        HexFormat hex = HexFormat.of();
        String hash = hex.formatHex(bytes); 
        
        return hash;
        
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
    }

    /**
     * Ejecuta un ataque de fuerza bruta utilizando el charset "abcdefABCDEF1234567890!".[cite: 1]
     * La longitud máxima de las contraseñas a probar será de 6 caracteres.[cite: 1]
     * Debe implementar bucles anidados para iterar cada posición (del 1 al 6) utilizando preferiblemente un char[].[cite: 1]
     * Asegúrate de poner la variable de clase npass a 0 al inicio y aumentarla en cada iteración.[cite: 2]
     */
    public String forcaBruta (String alg, String hash, String salt) {
        // Implementar ataque de fuerza bruta
        return null;
    }

    /**
     * Calcula la diferencia entre t1 y t2 y la devuelve formateada.[cite: 1]
     * El formato de salida esperado debe mostrar días, horas, minutos, segundos y milisegundos.[cite: 3]
     */
    public String getInterval (long t1, long t2) {
        // Implementar cálculo y formato de tiempo
        return null;
    }

    // Código aportado para el método main[cite: 2]
    public static void main(String[] args) throws Exception { 
        String salt = "qpoweiruañslkdfjz"; 
        String pw = "aaabF!"; 
        Hashes h = new Hashes(); 
        String[] aHashes = {h.getSHA512AmbSalt(pw, salt), 
        h.getPBKDF2AmbSalt(pw, salt) }; 
        String pwTrobat = null; 
        String[] algorismes = {"SHA-512", "PBKDF2"}; 
        
        for(int i=0; i< aHashes.length; i++){ 
            System.out.printf("===========================\n"); 
            System.out.printf("Algorisme: %s\n", algorismes[i]); 
            System.out.printf("Hash: %s\n",aHashes[i]); 
            System.out.printf("---------------------------\n"); 
            System.out.printf("--- Inici de força bruta ---\n"); 
            
            long t1 = System.currentTimeMillis(); 
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt); 
            long t2 = System.currentTimeMillis(); 
            
            System.out.printf("Pass : %s\n", pwTrobat); 
            System.out.printf("Provats: %d\n", h.npass); 
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2)); 
            System.out.printf("---------------------------\n\n"); 
        } 
    } 
}