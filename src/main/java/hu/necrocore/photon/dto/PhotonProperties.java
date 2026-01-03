package hu.necrocore.photon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotonProperties {

    @JsonProperty("osm_id")
    private Long osmId;

    /**
     * "N" (Node), "W" (Way), or "R" (Relation)
     */
    @JsonProperty("osm_type")
    private String osmType;

    @JsonProperty("osm_key")
    private String osmKey;

    @JsonProperty("osm_value")
    private String osmValue;

    private String name;

    private String country;

    /**
     * street, house, city, district, county, other, ...
     */
    private String type;

    @JsonProperty("countrycode")
    private String countryCode;

    private String city;

    private String postcode;

    private String street;

    private String housenumber;

    private String state;

    private String district;

    private String locality;

    private String county;

    private List<Double> extent;

    private Map<String, String> extra;
}