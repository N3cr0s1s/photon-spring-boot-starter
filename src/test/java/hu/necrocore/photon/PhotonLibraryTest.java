package hu.necrocore.photon;

import hu.necrocore.photon.client.PhotonClient;
import hu.necrocore.photon.dto.PhotonFeature;
import hu.necrocore.photon.dto.PhotonResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = PhotonLibraryTest.TestConfig.class)
class PhotonLibraryTest {

    @Configuration
    @EnableAutoConfiguration
    static class TestConfig {}

    @Autowired
    private PhotonClient photonClient;

    @Test
    void testSearch() {
        assertNotNull(photonClient, "Photon client not created");

        log.info("Start searching...");
        PhotonResponse response = photonClient.search("Budapest", "en", 1);

        assertNotNull(response);
        assertFalse(response.getFeatures().isEmpty());

        log.info("Found: {}" ,response.getFeatures().get(0).getProperties().getName());
    }

    @Test
    void testReverseGeocoding() {
        assertNotNull(photonClient, "Photon client not created");

        PhotonResponse response = photonClient.reverse(47.497913, 19.040236);
        assertNotNull(response);
        assertFalse(response.getFeatures().isEmpty());
    }

    @Test
    void testRichRealWorldLocation() {
        log.info("Searching for a rich location (Brandenburg Gate)...");
        // "Pariser Platz" in Berlin usually returns full address details
        PhotonResponse response = photonClient.search("Pariser Platz 1 Berlin", "en", 1);

        assertNotNull(response);
        assertFalse(response.getFeatures().isEmpty(), "Should find at least one result");

        PhotonFeature feature = response.getFeatures().get(0);
        var props = feature.getProperties();

        log.info("Testing fields for: {}", props.getName());
        log.info("osm type: {}", props.getOsmType());

        assertAll("Verify essential fields are filled",
                () -> assertNotNull(feature.getGeometry(), "Geometry should not be null"),
                () -> assertNotNull(feature.getGeometry().getCoordinates(), "Coordinates should not be null"),
                () -> assertEquals(2, feature.getGeometry().getCoordinates().size(), "Should have Lon/Lat"),

                () -> assertNotNull(props.getOsmId(), "OSM ID should be present"),
                () -> assertNotNull(props.getOsmType(), "OSM Type should be present"),

                () -> assertNotNull(props.getCountry(), "Country should be present"),
                () -> assertNotNull(props.getCity(), "City should be present"),
                () -> assertNotNull(props.getPostcode(), "Postcode should be present"),
                () -> assertNotNull(props.getStreet(), "Street should be present")
        );
    }
}