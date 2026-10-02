import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Polialfabetica {

    private static long clauSecreta = 12345678L;

    private static final String ALFABET_BASE =
        "aàábcçdeèéfghiíïjklmnñoòópqrstuúüvwxyz" + 
        "AÀÁBCÇDEÈÉFGHIÍÏJKLMNÑOÒÓPQRSTUÚÜVWXYZ";

    private static Random random;
    public static void initRandom(long clauSecreta) {
        random = new Random(clauSecreta);
    }

    public static List<Character> alfabetPermutat;

    public static void permutaAlfabet(){
        alfabetPermutat = new ArrayList<>();
        for(int i=0; i<ALFABET_BASE.length(); i++){
            alfabetPermutat.add(ALFABET_BASE.charAt(i));
        }
        Collections.shuffle(alfabetPermutat, random);
    }

    public static String xifraPoliAlfa(String msg){
        StringBuilder resultat = new StringBuilder();
        for(int i=0; i<msg.length(); i++){
            char c = msg.charAt(i);
            int idx = ALFABET_BASE.indexOf(c);
            if(idx!=-1){
                permutaAlfabet();
                resultat.append(alfabetPermutat.get(idx));
            }else{
                resultat.append(c);
            }
        }
        return resultat.toString();
        
    }
    public static String desxifraPoliAlfa(String msgXifrat){
        StringBuilder resultat = new StringBuilder();
        for(int i = 0; i < msgXifrat.length();i++){
            char c = msgXifrat.charAt(i);
            int idxBase = ALFABET_BASE.indexOf(c);
            if(idxBase != -1){
                permutaAlfabet();
                int idxPermutat = alfabetPermutat.indexOf(c);
                resultat.append(ALFABET_BASE.charAt(idxPermutat));
            } else{
                resultat.append(c);
            }
        }
        return resultat.toString();
    }
    public static void main(String[] args) {
        String msgs[] = {"Test 01 àrbitre, coixí, Perímetre",
                        "Test 02 Taüll, DÍA, año",
                        "Test 03 Peça, Òrrius, Bòvila"};
        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifratge:\n--------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);    
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }

        System.out.println("Desxifratge:\n-----------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }
}
