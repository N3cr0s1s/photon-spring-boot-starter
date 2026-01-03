package hu.necrocore.photon.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "hu.necrocore.photon")
public class PhotonConfig {

    private String baseUrl = "https://photon.komoot.io";
    private String defaultLang = "en";

}