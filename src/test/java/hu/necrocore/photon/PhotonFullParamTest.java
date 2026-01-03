package hu.necrocore.photon;

import hu.necrocore.photon.client.PhotonClient;
import hu.necrocore.photon.dto.PhotonResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = PhotonFullParamTest.TestConfig.class)
class PhotonFullParamTest {

    @Configuration
    @EnableAutoConfiguration
    static class TestConfig {}

    @Autowired
    private PhotonClient photonClient;

    @Test
    @DisplayName("Test search with location bias and basic parameters")
    void testSearchWithLocationBias() {
        log.info("Initiating search with location bias at Budapest coordinates");

        PhotonResponse response = photonClient.search(
                "Kossuth",
                "en",
                47.4979,
                19.0402,
                5,
                null,
                null,
                null
        );

        assertNotNull(response);
        assertTrue(response.getFeatures().size() <= 5, "Result size must not exceed limit");
        log.info("Search completed. Result count: {}", response.getFeatures().size());
    }

    @Test
    @DisplayName("Test search within geographic bounding box - Vienna, Austria")
    void testSearchWithBBox() {
        log.info("Initiating search within Vienna bounding box (lonMin, latMin, lonMax, latMax)");

        String viennaBbox = "16.18,48.11,16.55,48.32";

        PhotonResponse response = photonClient.search(
                "Museum",
                "en",
                null,
                null,
                10,
                viennaBbox,
                null,
                null
        );

        assertFalse(response.getFeatures().isEmpty(), "No results found in Vienna bounding box");

        String city = response.getFeatures().get(0).getProperties().getCity();
        log.info("Bounding box result verified for city: {}", city);

        assertTrue(city.equalsIgnoreCase("Wien") || city.equalsIgnoreCase("Vienna"),
                "Result should be within Vienna");
    }

    @Test
    @DisplayName("Test search with specific OSM tag filters")
    void testSearchWithOsmTags() {
        log.info("Initiating search with OSM tag filter: building:house");

        String[] tags = {"building:house"};

        PhotonResponse response = photonClient.search(
                "Budapest",
                "en",
                null,
                null,
                10,
                null,
                tags,
                null
        );

        assertNotNull(response);
        log.info("OSM tag filter search returned {} features", response.getFeatures().size());
    }

    @Test
    @DisplayName("Test search with additional dynamic parameters")
    void testSearchWithOtherParamsMap() {
        log.info("Initiating search with dynamic parameter map");

        Map<String, Object> extraParams = new HashMap<>();
        extraParams.put("location_bias_scale", 0.5);

        PhotonResponse response = photonClient.search(
                "Pharmacy",
                "en",
                47.4979,
                19.0402,
                3,
                null,
                null,
                extraParams
        );

        assertNotNull(response);
        assertFalse(response.getFeatures().isEmpty(), "Response features should not be empty");
        log.info("Dynamic parameter map injection successful");
    }

    @Test
    @DisplayName("Test complex scenario: Multiple constraints applied")
    void testComplexScenario() {
        log.info("Executing complex search scenario for residential buildings in Budapest metropolitan area");

        String budapestBbox = "18.90,47.40,19.20,47.60";
        String[] tags = {"building:house"};

        PhotonResponse response = photonClient.search(
                "Budapest",
                "en",
                47.497913,
                19.040236,
                20,
                budapestBbox,
                tags,
                null
        );

        assertNotNull(response);
        assertFalse(response.getFeatures().isEmpty(), "Expected at least one result for Budapest residential search");

        log.info("Complex search identified {} residential features", response.getFeatures().size());

        response.getFeatures().forEach(feature ->
                log.info("Feature details: [Name: {}, City: {}, Street: {}, House Number: {}]",
                        feature.getProperties().getName(),
                        feature.getProperties().getCity(),
                        feature.getProperties().getStreet(),
                        feature.getProperties().getHousenumber())
        );
    }
}