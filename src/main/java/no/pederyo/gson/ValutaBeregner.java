package no.pederyo.gson;

import java.io.IOException;
import java.util.Currency;
import java.util.Set;

public class ValutaBeregner {

    public static double BeregnValuta(String fra, String til, double belop) throws IOException {
        ExchangeRate rate;
        if (sjekkOmValutaStemmer(fra, til)) {
            rate = ExchangeRateService.getRate(fra, til);
        } else {
            return 0;
        }
        return rate.getRate() * belop;
    }

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
