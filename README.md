## JavaCryptoScraper

Program som tracker prisen på Iota og gir bruker notifikasjon om endringer.

Kjør jar med tre parametere: Antall Iota's, Investeringen din, PushBullet Api.
### Før du begynner
 * For at applikasjonen skal funkere må du ha pushbullet. Du finner apien din på hjemmesiden deres.
 * Sjekk at du har javaversjon > 8 innstalert.
### Last ned jar fil
* <a href="https://github.com/181221/h181221.github.io/raw/master/JavaScrapeIota/JavaScrapeIota.jar">JavaScrapeIota </a>
* Legg den i ønsket mappe
* Kjør: java -jar C:\Users\user\Documents\JavaScrapeIota\JavaScrapeIota.jar DineIotas TotalInvest PushBulletApi
* Eksempel: java -jar C:\Users\user\Documents\JavaScrapeIota\JavaScrapeIota.jar 100 5000 u.uasudhaisduah120

### Triggers på pushBullet og litt om parametere.
* Du får notifikasjon hver 12 time om et sammendrag dagens beholdning. 
* Milepæler 
  * IoTa når 4.5
  * IoTa når 5.5
  * IoTa når 6.5
* Hvert 30 sekund blir det IoTa sjekket den nye verdien har en differanse på +- 0.2 får du notifikasjon.
* Sjekkes for forskjell hver halvtime.
* Om du får en avkastning på 2% på din beholdning blir du varslet. Basert på Iotas verdi og din invistering.
