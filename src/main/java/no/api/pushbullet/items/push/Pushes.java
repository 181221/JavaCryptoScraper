package no.api.pushbullet.items.push;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pushes {

    @SerializedName("pushes")
    @Expose
    private List<Push> pushes = null;

    public List<Push> getPushes() {
        return pushes;
    }

    public void setPushes(List<Push> pushes) {
        this.pushes = pushes;
    }

}