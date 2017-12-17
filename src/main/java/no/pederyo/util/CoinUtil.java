package no.pederyo.util;

import no.pederyo.modell.Avkastning;
import no.pederyo.modell.AvkastningArkiv;
import no.pederyo.modell.Coin;
import no.pederyo.modell.Verdi;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import static no.pederyo.util.DatoUtil.lagCurrentDate;

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

    public static double gjorOmDoubleTilBC(double forje, String operator, double differanse) {
        BigDecimal b = new BigDecimal(forje);
        BigDecimal b1 = new BigDecimal(differanse);
        BigDecimal b2;
        switch (operator) {
            case "pluss":
                b2 = b.add(b1);
                break;
            case "minus":
                b2 = b.subtract(b1);
                break;
            case "gange":
                b2 = b.multiply(b1);
                break;
            default:
                return -1;
        }
        return formaterDouble(b2.doubleValue(), "##.0000");
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
     * @param verdi
     * @return double verdien
     */
    public static double formaterDouble(double verdi) {
        DecimalFormat f = new DecimalFormat("##.00");
        String verdien = f.format(verdi).toString().replace(',', '.');
        return Double.parseDouble(verdien);
    }

    public static double formaterDouble(double verdi, String pattern) {
        DecimalFormat f = new DecimalFormat(pattern);
        String verdien = f.format(verdi).toString().replace(',', '.');
        return Double.parseDouble(verdien);
    }

    public static double gjorOmDoubleTilBIGDECIMAL(double verdi) {
        BigDecimal b = new BigDecimal(verdi);
        return formaterDouble(b.doubleValue(), "##.0000");
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
        double nyTotal = gjorOmDoubleTilBIGDECIMAL(total);
        double nyInvest = gjorOmDoubleTilBIGDECIMAL(invest);
        return ((nyTotal - nyInvest) / nyInvest) * 100;
    }

    /**
     * Legger til iota prisen i pristabellen og setter opp attributtene til coinen.
     * Setter total og current-avkasning.
     * @param verdi
     * @param coin
     */
    public static boolean regnUtSetTotalOgAvkastning(double verdi, Coin coin) {
        boolean nyVerdi = false;
        if (verdi != -1) {
            coin.setTotal(CoinUtil.totalVerdiINOK(verdi, coin));
            Avkastning a = AvkastningArkiv.opprettAvkastning(coin);
            coin.setAvkasning(a);
            nyVerdi = true;
        } else {
            System.out.println("noe gikk galt");
        }
        return nyVerdi;
    }

    public static boolean leggTilVerdi(double verdi, Coin coin) {
        boolean lagtTil = false;
        if (verdi != -1) {
            coin.leggTil(lagVerdi(verdi));
            lagtTil = true;
        }
        return lagtTil;
    }

    private static Verdi lagVerdi(double pris) {
        return new Verdi(pris, lagCurrentDate());
    }
}
