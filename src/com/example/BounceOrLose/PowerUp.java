package com.example.BounceOrLose;

/**
 * Created by bernardot on 3/22/16.
 */
public class PowerUp {
    private int duration;
    private Constants.PowerUps type;

    public PowerUp(int duration, Constants.PowerUps type) {
        this.duration = duration;
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Constants.PowerUps getType() {
        return type;
    }

    public void setType(Constants.PowerUps type) {
        this.type = type;
    }
}
