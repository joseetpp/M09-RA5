package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;

public class XifradorRotX implements Xifrador {
    public static final char[] MINUSCULES = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz".toCharArray();
    public static final char[] MAJUSCULES = "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();

    private static final String MISSATGE_CLAU = "Clau de RotX ha de ser un sencer de 0 a 40";

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        int desplacament = validaClau(clau);
        String resultat = rota(msg, desplacament);
        return new TextXifrat(resultat.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        int desplacament = validaClau(clau);
        String text = new String(xifrat.getBytes(), StandardCharsets.UTF_8);
        return rota(text, -desplacament);
    }

    private int validaClau(String clau) throws ClauNoSuportada {
        if (clau == null) {
            throw new ClauNoSuportada(MISSATGE_CLAU);
        }
        int valor;
        try {
            valor = Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada(MISSATGE_CLAU);
        }
        if (valor < 0 || valor > 40) {
            throw new ClauNoSuportada(MISSATGE_CLAU);
        }
        return valor;
    }

    private String rota(String text, int desplacament) {
        int n = MINUSCULES.length;
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            int pos = indexDe(MINUSCULES, c);
            if (pos >= 0) {
                sb.append(MINUSCULES[Math.floorMod(pos + desplacament, n)]);
                continue;
            }
            pos = indexDe(MAJUSCULES, c);
            if (pos >= 0) {
                sb.append(MAJUSCULES[Math.floorMod(pos + desplacament, n)]);
                continue;
            }
            sb.append(c);
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
