package no.pederyo.klient;

import no.pederyo.modell.Coin;

import java.io.IOException;

import static no.pederyo.scraper.ScrapeHjelper.lagCoinFraArgs;
import static no.pederyo.scraper.Scraper.kjorProgram;
import static no.pederyo.scraper.Scraper.setUp;

/**
 * @author Peder
 * Program for å tracke prisen til IoTa.
 */
public class Klient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Coin coin = lagCoinFraArgs(args);
        setUp(args, coin);
        kjorProgram(coin);
    }
}
