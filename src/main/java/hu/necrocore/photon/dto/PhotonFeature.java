package hu.necrocore.photon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotonFeature {

    private String type;
    private PhotonGeometry geometry;
    private PhotonProperties properties;
    private @Nullable List<Double> bbox;

}