package src;

public class RotX {
    public static char[] minuscules = {
            'a', 'á', 'à', 'b', 'c', 'ç', 'd', 'e', 'é', 'è', 'f', 'g', 'h',
            'i', 'í', 'ì', 'ï', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'ó', 'ò',
            'p', 'q', 'r', 's', 't', 'u', 'ú', 'ù', 'ü', 'v', 'w', 'x', 'y', 'z'
    };
    public static char[] majuscules = {
            'A', 'Á', 'À', 'B', 'C', 'Ç', 'D', 'E', 'É', 'È', 'F', 'G', 'H',
            'I', 'Í', 'Ì', 'Ï', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'Ó', 'Ò',
            'P', 'Q', 'R', 'S', 'T', 'U', 'Ú', 'Ù', 'Ü', 'V', 'W', 'X', 'Y', 'Z'
    };
    public static int[] posicions = {0, 2, 4, 6};

    public static String minusculesStr = new String(minuscules);
    public static String majusculesStr = new String(majuscules);

    public static boolean esMajuscules (char c){
        if(Character.isUpperCase(c))return true ;
        return false;
    }
    public static boolean esMinuscules (char c){
        if(Character.isLowerCase(c))return true;
        return false;
    }
    public static String rotX(String cadena, int desplazament) {
        StringBuilder resultado = new StringBuilder();
        for (int i=0; i<cadena.length(); i++){
            if(esMajuscules(cadena.charAt(i))){
                int pos = majusculesStr.indexOf(cadena.charAt(i));
                int novaPos = (pos + desplazament + majuscules.length) % majuscules.length;
                resultado.append(majuscules[novaPos]);
            }else if(esMinuscules(cadena.charAt(i))){
                int pos = minusculesStr.indexOf(cadena.charAt(i));
                int novaPos = (pos + desplazament + minuscules.length) % minuscules.length ;
                resultado.append(minuscules[novaPos]);
            } else {
                resultado.append(cadena.charAt(i));
            }
        }

        return resultado.toString();
    }

    public static String xifraRotX(String cadena, int desplazament){
        return rotX(cadena, desplazament);
    }
    public static String desxifraRotX(String cadena, int desplazament){
        return rotX(cadena, -desplazament);
    }
    
    public static String forcaBrutaRot(String cadenaXifrada){
        StringBuilder resultado = new StringBuilder();

        for(int i = 0; i < majuscules.length; i++){
            resultado.append("(" + i + ") " + rotX(cadenaXifrada, -i) + "\n");
        }

        return resultado.toString();
    }
    public static void main(String[] args) {
        String msgs[] = {"ABC", "XYZ", "hOLA, Mr. calçot", "Perdó, per tu què és?"} ;
        String msgXifrat[] = new String[msgs.length];

        System.out.println("\nXifrat\n-------------");

        for(int i=0; i<msgXifrat.length; i++){
            msgXifrat[i] = xifraRotX(msgs[i], posicions[i]);
            System.out.printf("(%d) %-23s => %s%n",posicions[i],  msgs[i], msgXifrat[i]);
        }
        System.out.printf("\nDesxifrat\n-----------\n");
        
        for(int i = 0; i < msgXifrat.length; i++){
            System.out.printf("(%d) %-23s => %s%n",posicions[i], msgXifrat[i], desxifraRotX(msgXifrat[i], posicions[i] ));
        }

        System.out.printf("\nForça Bruta\n-------------\n");
        
        System.out.println(forcaBrutaRot(msgXifrat[3]));
    }   
}