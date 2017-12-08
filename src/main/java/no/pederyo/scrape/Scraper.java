package no.pederyo.scrape;

import no.pederyo.coin.Coin;

import java.io.IOException;

import static no.pederyo.coin.CoinUtil.regnUtVerdier;
import static no.pederyo.scrape.ScrapeHjelper.*;

public class Scraper {
    public static void main(String[] args) throws IOException, InterruptedException {
        Coin coin = lagCoinFraArgs(args);
        while (true) {
            try {
                double verdi = scrape();
                regnUtVerdier(verdi, coin);
                skrivUt(verdi, coin);
                System.out.println();
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


}
