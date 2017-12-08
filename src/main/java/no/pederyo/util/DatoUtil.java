package no.pederyo.util;

import java.time.LocalDate;
import java.util.Calendar;

public class DatoUtil {
    /**
     * Lager dagens dato i streng format
     *
     * @return dd.mm.yyyy HH:mm
     */
    public static String lagCurrentDate() {
        LocalDate localDate = LocalDate.now();
        return fraEngTilNorskDato(localDate.toString()) + " " + getKlokkeSlett();
    }

    private static String fraEngTilNorskDato(String dato) {
        String[] arr = dato.split("-");
        for (int i = 0; i < arr.length - 1; i++) {
            String tmp = arr[i];
            arr[i] = arr[i + 1];
            arr[i + 1] = tmp;
        }
        String tmp = arr[0];
        arr[0] = arr[1];
        arr[1] = tmp;
        String nydato = String.join(".", arr);
        return nydato;
    }

    private static String getKlokkeSlett() {
        return Calendar.getInstance().getTime().toString().substring(11, 16);
    }
}
