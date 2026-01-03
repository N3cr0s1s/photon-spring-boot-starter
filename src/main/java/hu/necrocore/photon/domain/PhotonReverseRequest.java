package hu.necrocore.photon.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhotonReverseRequest {

    private final GeoPoint location;
    private final Integer limit;
    private final String language;
    private final Double radius;
    private final Boolean distanceSort;

    public static PhotonReverseRequestBuilder builder(GeoPoint location) {
        return new PhotonReverseRequestBuilder()
                .location(location)
                .limit(1)
                .language("en")
                .distanceSort(true);
    }
}
