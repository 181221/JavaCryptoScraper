package no.pederyo.gson;

/**
 * {"success":true,
 * "source":"USD",
 * "target":"EUR",
 * "rate":"0.8996300",
 * "amount":0.9,
 * "message":""}
 */
public class ExchangeRate {

    private boolean success;
    private String source;
    private String target;
    private double rate;
    private double amount;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
