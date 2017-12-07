package no.pederyo.coin;

import no.pederyo.gson.ValutaBeregner;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.DecimalFormat;

public class CoinUtil {
    public static double totalVerdiINOK(double verdi, Coin coin) {
        double total = -1.0;
        try {
            total = ValutaBeregner.BeregnValuta("USD", "NOK", coin.getAntall() * verdi);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

    public static String formaterTall(double verdi) {
        DecimalFormat f = new DecimalFormat("##.00");
        return f.format(verdi);
    }

    public static double konverterTilTall(Element elem) {
        String tall = elem.text();
        Double verdi = -1.0;
        try {
            tall = tall.substring(2, 8);
            verdi = Double.parseDouble(tall);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getStackTrace());
        }
        return verdi;
    }

    public void leggInnCoins(int antall) {

    }
}
