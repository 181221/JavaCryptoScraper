package no.pederyo.util;

import no.pederyo.modell.Avkastning;
import no.pederyo.modell.Coin;
import no.pederyo.modell.Verdi;
import no.pederyo.scraper.PushBullet;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import static no.pederyo.scraper.VerdiSjekker.gjorOmDoubleTilBC;

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
            double avkasning = rengUtProsent(coin.getTotal(), coin.getInvestment());
            coin.setAvkasning(avkasning);
            nyVerdi = true;
        } else {
            System.out.println("noe gikk galt");
        }
        return nyVerdi;
    }


    /**
     * Regner ut differansen mellom current verdi og forje.
     * sjekker verditabellen om det er prisendring
     * @param c
     * @param verdi realtime iota verdi i dollar
     * @return returnerer true om det har vært en endring på +-0.3.
     */
    public static boolean sjekkValletPushNot(Coin c, double verdi) {
        int antall = c.getVerdier().size();
        double plussforje = gjorOmDoubleTilBC(c.seForjePris(), "pluss", 0.3);
        double minusforje = gjorOmDoubleTilBC(c.seForjePris(), "minus", 0.3);
        boolean nyVerdi = false;
        if (verdi >= plussforje && antall >= 2) {
            PushBullet.client.sendNotePush("Stigning på Iota", formaterTall(verdi) + " økning på over 0.3");
            nyVerdi = true;
        } else if (verdi <= minusforje && antall >= 2) {
            PushBullet.client.sendNotePush("Iota droppet siden", formaterTall(verdi) + " nedgang på over 0.3");
            nyVerdi = true;
        }
        return nyVerdi;
    }

    /**
     * Regner ut avkasntning.
     *
     * @param c
     * @param avk
     * @return
     */
    public static boolean sjekkAvkastning(Avkastning avk) {
        boolean nyAvkasning = false;
        if (avk.getAntall() >= 2) {
            double forje = avk.getStart().getNeste().getElement();
            double current = avk.getStart().getElement();
            double plussforje = gjorOmDoubleTilBC(forje, "pluss", 5);
            double minusforje = gjorOmDoubleTilBC(forje, "minus", 5);
            if (current >= plussforje) {
                //PushBullet.client.sendNotePush("pluss-Avkastning", formaterTall(current + "%");
                nyAvkasning = true;
            } else if (current <= minusforje) {
                //PushBullet.client.sendNotePush("minus-Avkastning", formaterTall(current + "%");
                nyAvkasning = true;
            }
        }
        return nyAvkasning;
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
        return new Verdi(pris, DatoUtil.lagCurrentDate());
    }
}
