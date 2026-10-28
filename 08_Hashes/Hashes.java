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

    // Ejecuta un ataque de fuerza bruta utilizando el charset "abcdefABCDEF1234567890!".
    public String forcaBruta (String alg, String hash, String salt) {
        npass = 0;
        //Definimos el charset indicado en el enunciado
        char[] charset = "abcdefABCDEF1234567890!".toCharArray();
        // Bucles anidados para probar exhaustivamente las combinaciones
        // Iteramos 6 veces
        // --- Longitud 1 ---
        for (int i = 0; i < charset.length; i++) {
            char[] pw = new char[]{ charset[i] };
            if (comprovarHash(pw, alg, hash, salt)) return new String(pw);
        }

        // --- Longitud 2 ---
        for (int i = 0; i < charset.length; i++) {
            for (int j = 0; j < charset.length; j++) {
                char[] pw = new char[]{ charset[i], charset[j] };
                if (comprovarHash(pw, alg, hash, salt)) return new String(pw);
            }
        }

        // --- Longitud 3 ---
        for (int i = 0; i < charset.length; i++) {
            for (int j = 0; j < charset.length; j++) {
                for (int k = 0; k < charset.length; k++) {
                    char[] pw = new char[]{ charset[i], charset[j], charset[k] };
                    if (comprovarHash(pw, alg, hash, salt)) return new String(pw);
                }
            }
        }

        // --- Longitud 4 ---
        for (int i = 0; i < charset.length; i++) {
            for (int j = 0; j < charset.length; j++) {
                for (int k = 0; k < charset.length; k++) {
                    for (int l = 0; l < charset.length; l++) {
                        char[] pw = new char[]{ charset[i], charset[j], charset[k], charset[l] };
                        if (comprovarHash(pw, alg, hash, salt)) return new String(pw);
                    }
                }
            }
        }

        // --- Longitud 5 ---
        for (int i = 0; i < charset.length; i++) {
            for (int j = 0; j < charset.length; j++) {
                for (int k = 0; k < charset.length; k++) {
                    for (int l = 0; l < charset.length; l++) {
                        for (int m = 0; m < charset.length; m++) {
                            char[] pw = new char[]{ charset[i], charset[j], charset[k], charset[l], charset[m] };
                            if (comprovarHash(pw, alg, hash, salt)) return new String(pw);
                        }
                    }
                }
            }
        }

        // --- Longitud 6 ---
        for (int i = 0; i < charset.length; i++) {
            for (int j = 0; j < charset.length; j++) {
                for (int k = 0; k < charset.length; k++) {
                    for (int l = 0; l < charset.length; l++) {
                        for (int m = 0; m < charset.length; m++) {
                            for (int n = 0; n < charset.length; n++) {
                                char[] pw = new char[]{ charset[i], charset[j], charset[k], charset[l], charset[m], charset[n] };
                                if (comprovarHash(pw, alg, hash, salt)) return new String(pw);
                            }
                        }
                    }
                }
            }
        }

        return null; // Si no encuentra nada tras probar hasta 6 caracteres
    }
    //Sirve para validar la contraseña
    private boolean comprovarHash(char[] pwChars, String alg, String hashOriginal, String salt) {
        // Incrementamos el contador con cada password provado
        npass++; 
        
        String pwString = new String(pwChars);
        String hashGenerado = null;
        
        // Dependiendo del algoritmo, llamamos a un método o a otro
        if (alg.equals("SHA-512")) {
            hashGenerado = getSHA512AmbSalt(pwString, salt);
        } else if (alg.equals("PBKDF2")) {
            hashGenerado = getPBKDF2AmbSalt(pwString, salt);
        }
        
        // Comprobamos si el hash que acabamos de generar coincide con el que buscamos
        return hashOriginal.equals(hashGenerado);
    }
    //Calcula la diferencia entre t1 y t2 y la devuelve formateada
    public String getInterval(long t1, long t2) {
        //Calculamos la diferencia total en milisegundos
        long diferencia = t2 - t1;
        
        //Extraemos las unidades de tiempo usando divisiones y el operador módulo (%)
        long millis = diferencia % 1000; 
        long segons = (diferencia / 1000) % 60; 
        long minuts = (diferencia / (1000 * 60)) % 60; 
        long hores = (diferencia / (1000 * 60 * 60)) % 24; 
        long dies = diferencia / (1000 * 60 * 60 * 24); 
        
        // Formateamos el string exactamente como pide el enunciado[cite: 3]
        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis", 
                            dies, hores, minuts, segons, millis);
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