package hu.necrocore.photon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotonGeometry {

    private String type;
    private List<Double> coordinates;

    public @Nullable Double getLongitude() {
        return (coordinates != null && !coordinates.isEmpty()) ? coordinates.get(0) : null;
    }

    public @Nullable Double getLatitude() {
        return (coordinates != null && coordinates.size() > 1) ? coordinates.get(1) : null;
    }
}