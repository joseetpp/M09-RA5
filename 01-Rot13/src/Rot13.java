public class Rot13 {
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
    // Por tema de eficiencia creamos un nuevo objeto de minusculas/majusculas al
    // iniciar la clase, para evitar ponerlo en el bucle y crear muchos objetos
    public static String minusculesStr = new String(minuscules);
    public static String majusculesStr = new String(majuscules);

    public static final int DESPLAZAMENT = 13;

    public static String xifraRot13(String cadena) {
        StringBuilder cadenaXifrada = new StringBuilder();
        for (int i = 0; i < cadena.length(); i++) {
            if (Character.isUpperCase(cadena.charAt(i))) {
                int pos = majusculesStr.indexOf(cadena.charAt(i));
                int novaPos = (pos + DESPLAZAMENT) % majuscules.length;
                cadenaXifrada.append(majuscules[novaPos]);
            } else if (Character.isLowerCase(cadena.charAt(i))) {
                int pos = minusculesStr.indexOf(cadena.charAt(i));
                int novaPos = (pos + DESPLAZAMENT) % minuscules.length;
                cadenaXifrada.append(minuscules[novaPos]);
            } else {
                cadenaXifrada.append(cadena.charAt(i));
            }
        }
        return cadenaXifrada.toString();
    }

    public static String desxifraRot13(String cadena) {
        StringBuilder cadenaDesxifrada = new StringBuilder();
        for (int i = 0; i < cadena.length(); i++) {
            if (Character.isUpperCase(cadena.charAt(i))) {
                int pos = majusculesStr.indexOf(cadena.charAt(i));
                int novaPos = (pos - DESPLAZAMENT + majuscules.length) % majuscules.length;
                cadenaDesxifrada.append(majuscules[novaPos]);
            } else if (Character.isLowerCase(cadena.charAt(i))) {
                int pos = minusculesStr.indexOf(cadena.charAt(i));
                int novaPos = (pos - DESPLAZAMENT + minuscules.length) % minuscules.length;
                cadenaDesxifrada.append(minuscules[novaPos]);
            } else {
                cadenaDesxifrada.append(cadena.charAt(i));
            }
        }
        return cadenaDesxifrada.toString();
    }
    public static void main(String[] args){
        String msgs[] = {"ABC","XYZ","Hola, Mr. calçot", "Perdó, per tu què és?"};
        String msgXifrat[] = new String[msgs.length];

        System.out.println("\nXifrat\n-------------");

        for(int i=0; i<msgXifrat.length; i++){
            msgXifrat[i] = xifraRot13(msgs[i]);
            System.out.printf("%-23s => %s%n", msgs[i], msgXifrat[i]);
        }
        System.out.printf("\nDesxifrat\n--------");
        
        for(String msg: msgXifrat){
            System.out.printf("%-23s => %s%n", msg, desxifraRot13(msg));
        }
    }
}