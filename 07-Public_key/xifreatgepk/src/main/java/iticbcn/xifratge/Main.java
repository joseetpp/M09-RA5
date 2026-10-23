package iticbcn.xifratge;

import java.security.KeyPair;
import javax.xml.bind.DatatypeConverter;

public class Main {
    public static void main(String args[]) throws Exception {
        // Instancia el objeto de tu clase donde implementarás los algoritmos
        ClauPublica cp = new ClauPublica();
        
        // Define el mensaje original (incluye acentos y diéresis para probar la codificación)
        String msg = "Missatge de prova per xifrar áéíóú àèìòù äëïöü";
        
        // Genera el par de claves RSA: una pública (para cifrar) y una privada (para descifrar)
        KeyPair parellClaus = cp.generaParellClausRSA();
        
        // Llama a tu método para cifrar el mensaje usando la clave pública generada
        byte[] msgXifrat = cp.xifraRSA(msg, parellClaus.getPublic());
        
        System.out.println("===============================");
        System.out.print("Text xifrat: ");
        // Convierte los bytes ininteligibles del texto cifrado a formato hexadecimal para imprimirlo
        System.out.println(DatatypeConverter.printHexBinary(msgXifrat));
        
        // Llama a tu método para descifrar el mensaje usando la clave privada correspondiente
        String msgDesxifrat = cp.desxifraRSA(msgXifrat, parellClaus.getPrivate());
        
        System.out.println("===============================");
        // Imprime el texto ya devuelto a su estado original
        System.out.println("Text desxifrat: " + msgDesxifrat);
        
        // Extrae los bytes de la clave pública y los convierte a formato hexadecimal
        String strClauPub = DatatypeConverter.printHexBinary(
            parellClaus.getPublic().getEncoded());
            
        // Extrae los bytes de la clave privada y los convierte a formato hexadecimal
        String strClauPriv = DatatypeConverter.printHexBinary(
            parellClaus.getPrivate().getEncoded());
            
        System.out.println("===============================");
        System.out.println("Clau pública: " + strClauPub);
        System.out.println("===============================");
        System.out.println("Clau privada: " + strClauPriv);
    }
}