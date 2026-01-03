package hu.necrocore.photon.service;

import hu.necrocore.photon.client.PhotonClient;
import hu.necrocore.photon.domain.GeoPoint;
import hu.necrocore.photon.domain.PhotonReverseRequest;
import hu.necrocore.photon.domain.PhotonSearchRequest;
import hu.necrocore.photon.dto.PhotonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class PhotonService {

    private final PhotonClient photonClient;

    /**
     * Performs a forward geocoding search using the provided request parameters.
     *
     * @param request the search request parameters
     * @return the search response
     * @throws IllegalArgumentException if the search query is empty
     */
    public PhotonResponse search(PhotonSearchRequest request) throws IllegalArgumentException {
        validateSearch(request);

        GeoPoint bias = request.getLocationBias();

        return photonClient.search(
                request.getQuery(),
                request.getLanguage(),
                bias != null ? bias.getLatitude() : null,
                bias != null ? bias.getLongitude() : null,
                request.getLimit(),
                request.getBoundingBox() != null ? request.getBoundingBox().asString() : null,
                CollectionUtils.isEmpty(request.getOsmTags())
                        ? null
                        : request.getOsmTags().toArray(String[]::new),
                request.getExtraParams()
        );
    }

    /**
     * Performs a reverse geocoding operation using the provided request parameters.
     *
     * @param request the reverse request containing the location, limit, language, radius,
     *                and distance sort preferences
     * @return the reverse geocoding response containing details about the location
     * @throws IllegalArgumentException if the request does not contain a valid location
     */
    public PhotonResponse reverse(PhotonReverseRequest request) {
        validateReverse(request);

        GeoPoint location = request.getLocation();

        return photonClient.reverse(
                location.getLatitude(),
                location.getLongitude(),
                request.getLimit(),
                request.getLanguage(),
                request.getRadius(),
                request.getDistanceSort()
        );
    }

    /**
     * Validates the given forward geocoding search request.
     *
     * @param request the PhotonSearchRequest instance containing search parameters
     * @throws IllegalArgumentException if the query parameter in the request is null or blank
     */
    private void validateSearch(PhotonSearchRequest request) throws IllegalArgumentException {
        if (request.getQuery() == null) {
            throw new IllegalArgumentException("Photon search query must not be empty");
        }
    }

    /**
     * Validates the given reverse geocoding request to ensure it contains the required parameters.
     *
     * @param request the PhotonReverseRequest instance containing the parameters for reverse geocoding
     * @throws IllegalArgumentException if the location parameter in the request is null
     */
    private void validateReverse(PhotonReverseRequest request) throws IllegalArgumentException {
        if (request.getLocation() == null) {
            throw new IllegalArgumentException("Photon reverse request requires a location");
        }
    }
}
