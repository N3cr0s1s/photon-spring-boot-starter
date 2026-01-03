package hu.necrocore.photon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotonResponse {

    private String type;
    private List<PhotonFeature> features;

}