package no.api.pushbullet.items.subscription;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Subscriptions {

    @SerializedName("subscriptions")
    @Expose
    private List<Subscription> subscriptions = null;

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

}