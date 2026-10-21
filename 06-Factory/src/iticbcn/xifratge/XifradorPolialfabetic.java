package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {
    public static final char[] ALFABET = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz".toCharArray();

    private Random random;

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        long llavor;
        try {
            llavor = Long.parseLong(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }
        initRandom(llavor);
        String resultat = processa(msg, true);
        return new TextXifrat(resultat.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        long llavor;
        try {
            llavor = Long.parseLong(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau de Polialfabètic ha de ser un String convertible a long");
        }
        initRandom(llavor);
        String text = new String(xifrat.getBytes(), StandardCharsets.UTF_8);
        return processa(text, false);
    }

    // El random s'inicialitza abans de xifrar i abans de desxifrar
    private void initRandom(long llavor) {
        random = new Random(llavor);
    }

    private char[] permutaAlfabet() {
        List<Character> llista = new ArrayList<>();
        for (char c : ALFABET) {
            llista.add(c);
        }
        Collections.shuffle(llista, random);
        char[] resultat = new char[ALFABET.length];
        for (int i = 0; i < resultat.length; i++) {
            resultat[i] = llista.get(i);
        }
        return resultat;
    }

    private String processa(String text, boolean xifrar) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            // Una nova permutació per a cada caràcter (poli-alfabètic)
            char[] permutat = permutaAlfabet();
            char[] origen = xifrar ? ALFABET : permutat;
            char[] desti = xifrar ? permutat : ALFABET;

            boolean majuscula = Character.isUpperCase(c);
            char minuscula = Character.toLowerCase(c);
            int pos = indexDe(origen, minuscula);
            if (pos >= 0) {
                char nou = desti[pos];
                sb.append(majuscula ? Character.toUpperCase(nou) : nou);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private int indexDe(char[] alfabet, char c) {
        for (int i = 0; i < alfabet.length; i++) {
            if (alfabet[i] == c) {
                return i;
            }
        }
        return -1;
    }
}
