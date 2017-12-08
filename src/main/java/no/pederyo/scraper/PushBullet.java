package no.pederyo.scraper;

import no.api.pushbullet.PushbulletClient;
import no.api.pushbullet.items.device.Device;

import java.util.List;

public class PushBullet {
    public static PushbulletClient client;

    public PushBullet(String apikey) {
        client = new PushbulletClient(apikey);
    }

    public void printDivices() {
        List<Device> devices = client.listDevices();
        for (Device d : devices) {
            System.out.println(d.getNickname() + " \t\t" + d.getIden());
        }
    }

    public void pushNote(String tittel, String melding) {
        client.sendNotePush(tittel, melding);
    }

    public PushbulletClient getClient() {
        return client;
    }

    public void setClient(PushbulletClient client) {
        PushBullet.client = client;
    }
}
