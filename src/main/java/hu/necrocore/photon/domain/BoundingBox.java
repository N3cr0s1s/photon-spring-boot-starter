package hu.necrocore.photon.domain;

import lombok.Value;

@Value
public class BoundingBox {
    double lonMin;
    double latMin;
    double lonMax;
    double latMax;

    public String asString() {
        return lonMin + "," + latMin + "," + lonMax + "," + latMax;
    }
}