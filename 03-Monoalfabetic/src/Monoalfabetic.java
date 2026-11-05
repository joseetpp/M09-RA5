import java.util.Random;
public class Monoalfabetic {
    public final char[] MAJUSCULES = {
            'A', 'Á', 'À', 'B', 'C', 'Ç', 'D', 'E', 'É', 'È', 'F', 'G', 'H',
            'I', 'Í', 'Ì', 'Ï', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'Ó', 'Ò',
            'P', 'Q', 'R', 'S', 'T', 'U', 'Ú', 'Ù', 'Ü', 'V', 'W', 'X', 'Y', 'Z'
    };
    private char[] alfabetPermutat;
    public Monoalfabetic() {
        this.alfabetPermutat = permutaAlfabet(MAJUSCULES);
    }

    //generi una permutació de l'alfabet complet amb accents greus
    public char[] permutaAlfabet(char[] alfabet) {
        Random random = new Random();
        char[] nouAlfabet = alfabet.clone();

        //Algoritmo Fisher-Yates (recorre de atrás hacia adelante intercambiando posiciones)
        for (int i = nouAlfabet.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1); 
            
            char temp = nouAlfabet[i];
            nouAlfabet[i] = nouAlfabet[j];
            nouAlfabet[j] = temp;
        }

        return nouAlfabet;
    }
    private int buscaIndex(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        return -1; 
    }
        // xifre la cadena passada com a paràmetre amb xifratge monoalfabètic utilitzant la permutació generada inicialment
    public String xifraMonoAlfa(String cadena){
        if(cadena==null) return null;
        StringBuilder resultat = new StringBuilder();
        for(int i=0; i<cadena.length(); i++ ){
            char original = cadena.charAt(i);
            boolean esMinuscula = Character.isLowerCase(original);
            char enMajuscula = Character.toUpperCase(original);

            int index = buscaIndex(MAJUSCULES, enMajuscula);
            if(index!=-1){
                // obtenemos la letra permutada
                char xifrat = alfabetPermutat[index];
                resultat.append(esMinuscula ? Character.toLowerCase(xifrat) : xifrat);
            } else {
                resultat.append(original);
            }
        }
        return resultat.toString();
    }
    // desxifre la cadena del paràmetre i torni una cadena dexifrada ambmonoalfabètic.
    public String desxifraMonoAlfa(String xifrat) {
        if (xifrat == null) return null;
        StringBuilder resultat = new StringBuilder();

        for (int i = 0; i < xifrat.length(); i++) {
            char original = xifrat.charAt(i);
            boolean esMinuscula = Character.isLowerCase(original);
            char enMajuscula = Character.toUpperCase(original);

            // En el descifrado buscamos en el alfabeto permutado
            int index = buscaIndex(alfabetPermutat, enMajuscula);

            if (index != -1) {
                // Recuperamos la letra del alfabeto original
                char desxifrat = MAJUSCULES[index];
                resultat.append(esMinuscula ? Character.toLowerCase(desxifrat) : desxifrat);
            } else {
                resultat.append(original);
            }
        }
        return resultat.toString();
    }

    public char[] getAlfabetPermutat() {
        return alfabetPermutat;
    }
    public static void main(String[] args) {
        Monoalfabetic mono = new Monoalfabetic();

        //Mostrar alfabeto original
        for (char c : mono.MAJUSCULES) {
            System.out.print(c + " ");
        }
        System.out.println();

        for (char c : mono.getAlfabetPermutat()) {
            System.out.print(c + " ");
        }
        System.out.println("\n");

        String[] proves = {
            "Test 01 àrbitre, coixí, Perímetre",
            "Test 02 Taüll, DÍA, año",
            "Test 03 Peça, Òrrius, Bòvila"
        };

        System.out.println("Xifratge:");
        String[] xifrats = new String[proves.length];
        for (int i = 0; i < proves.length; i++) {
            xifrats[i] = mono.xifraMonoAlfa(proves[i]);
            System.out.println(proves[i] + " -> " + xifrats[i]);
        }
        System.out.println();

        System.out.println("Desxifratge:");
        for (int i = 0; i < xifrats.length; i++) {
            String desxifrat = mono.desxifraMonoAlfa(xifrats[i]);
            System.out.println(xifrats[i] + " -> " + desxifrat);
        }
    }
}