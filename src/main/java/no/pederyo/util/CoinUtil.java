package no.pederyo.util;

import no.pederyo.modell.Coin;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.DecimalFormat;

public class CoinUtil {
    /**
     * Ganger ut antall IoTa med verdien til den med i USD og
     * konverterer beholdingen din til norskekroner.
     *
     * @param verdi til IoTa
     * @param coin  IoTa coinen
     * @return returnerer totale beholdningen din i norske kroner.
     */
    private static double totalVerdiINOK(double verdi, Coin coin) {
        double total = -1.0;
        try {
            total = ValutaBeregner.BeregnValuta("USD", "NOK", coin.getAntall() * verdi);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * Formaterer en double til en streng med 2 desimaler.
     *
     * @param verdi
     * @return streng
     */
    public static String formaterTall(double verdi) {
        DecimalFormat f = new DecimalFormat("##.00");
        return f.format(verdi);
    }

    /**
     * Formaterer en double til en double med 2 desimaler.
     *
     * @param verdi
     * @return double verdien
     */
    public static double formaterDouble(double verdi) {
        DecimalFormat f = new DecimalFormat("##.00");
        String verdien = f.format(verdi).toString().replace(',', '.');
        return Double.parseDouble(verdien);
    }

    /**
     * Konvererterer prisen til iota fra html doc til en double.
     * @param elem
     * @return prisen i double.
     */
    public static double konverterTilTall(Element elem) {
        String tall = elem.text();
        Double verdi = -1.0;
        try {
            tall = tall.substring(2, 8);
            verdi = Double.parseDouble(tall);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return verdi;
    }

    /**
     * Regner ut prosent økning/nedgang.
     *
     * @param total
     * @param invest
     * @return
     */
    public static double rengUtProsent(double total, double invest) {
        return ((total - invest) / invest) * 100;
    }

    /**
     * Legger til iota prisen i pristabellen og setter opp attributtene til coinen.
     * Setter total og avkasning.
     * @param verdi
     * @param coin
     */
    public static void regnUtVerdier(double verdi, Coin coin) {
        if (verdi != -1) {
            coin.leggTil(verdi);
            coin.setTotal(CoinUtil.totalVerdiINOK(verdi, coin));
            double avkasning = rengUtProsent(coin.getTotal(), coin.getInvestment());
            coin.setAvkasning(avkasning);
        } else {
            System.out.println("noe gikk galt");
        }
    }

    public static void regnUtTid(double verdi, Coin c) {
        DatoUtil.lagCurrentDate();
    }
}
