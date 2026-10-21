package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XifradorMonoalfabetic implements Xifrador {
    public static final char[] ALFABET = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz".toCharArray();

    private static final String MISSATGE_CLAU = "Xifratxe monoalfabètic no suporta clau != null";

    private final char[] permutat;

    // La permutació s'inicialitza al constructor
    public XifradorMonoalfabetic() {
        List<Character> llista = new ArrayList<>();
        for (char c : ALFABET) {
            llista.add(c);
        }
        Collections.shuffle(llista);
        permutat = new char[ALFABET.length];
        for (int i = 0; i < permutat.length; i++) {
            permutat[i] = llista.get(i);
        }
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada(MISSATGE_CLAU);
        }
        String resultat = substitueix(msg, ALFABET, permutat);
        return new TextXifrat(resultat.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada(MISSATGE_CLAU);
        }
        String text = new String(xifrat.getBytes(), StandardCharsets.UTF_8);
        return substitueix(text, permutat, ALFABET);
    }

    private String substitueix(String text, char[] origen, char[] desti) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
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
