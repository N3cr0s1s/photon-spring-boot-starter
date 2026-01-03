package hu.necrocore.photon.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Builder
public class PhotonSearchRequest {

    private final String query;
    private final String language;
    private final GeoPoint locationBias;
    private final Integer limit;
    private final BoundingBox boundingBox;
    private final Set<String> osmTags;
    private final Map<String, Object> extraParams;

    public static PhotonSearchRequestBuilder builder(String query) {
        return new PhotonSearchRequestBuilder()
                .query(query)
                .language("en")
                .limit(10)
                .extraParams(new HashMap<>());
    }
}
