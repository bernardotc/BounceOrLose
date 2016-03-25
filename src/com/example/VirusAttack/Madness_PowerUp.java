package com.example.VirusAttack;

import java.io.Serializable;

/**
 * Created by bernardot on 3/23/16.
 */
public class Madness_PowerUp extends PowerUp implements Serializable {
    public Madness_PowerUp() {
        super();
        setDuration(200);
        setType(Constants.PowerUps.MADNESS);
    }
}
