package no.pederyo.gson;

import java.io.IOException;
import java.util.Currency;
import java.util.Set;

public class ValutaBeregner {
    /**
     * Sjekker at valutaen stemmer.
     * Henter raten fra internett og regner ut belopet.
     *
     * @param fra   USD
     * @param til   NOK
     * @param belop iota*USD
     * @return belop.
     * @throws IOException
     */
    public static double BeregnValuta(String fra, String til, double belop) throws IOException {
        ExchangeRate rate;
        if (sjekkOmValutaStemmer(fra, til)) {
            rate = ExchangeRateService.getRate(fra, til);
        } else {
            return 0;
        }
        return rate.getRate() * belop;
    }

    /**
     * Sjekker om Valutaen kodene stemmer.
     * @param fra
     * @param til
     * @return true om valutaen fins, false ellers.
     */
    private static boolean sjekkOmValutaStemmer(String fra, String til) {
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        int i = 0;
        for (Currency c : currencies) {
            if (c.toString().equals(fra) || c.toString().equals(til)) {
                i++;
            }
        }
        return i == 2;
    }
}
