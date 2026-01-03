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

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = PhotonReverseParamTest.TestConfig.class)
class PhotonReverseParamTest {

    @Configuration
    @EnableAutoConfiguration
    static class TestConfig {}

    @Autowired
    private PhotonClient photonClient;

    @Test
    @DisplayName("Reverse geocoding with basic latitude and longitude in Budapest")
    void testReverseBasicBudapest() {
        log.info("Initiating basic reverse geocoding in Budapest");

        PhotonResponse response = photonClient.reverse(
                47.4979,
                19.0402,
                null,
                null,
                null,
                null
        );

        assertNotNull(response);
        assertFalse(response.getFeatures().isEmpty(), "Expected at least one reverse result");
        log.info("Reverse geocoding completed with {} features", response.getFeatures().size());
    }

    @Test
    @DisplayName("Reverse geocoding with limit and language in Vienna")
    void testReverseWithLimitAndLangVienna() {
        log.info("Initiating reverse geocoding in Vienna with limit and language");

        PhotonResponse response = photonClient.reverse(
                48.2082,
                16.3738,
                5,
                "en",
                null,
                null
        );

        assertNotNull(response);
        assertTrue(response.getFeatures().size() <= 5, "Result size must not exceed limit");

        String city = response.getFeatures().get(0).getProperties().getCity();
        log.info("Top reverse result city: {}", city);

        assertTrue(city.equalsIgnoreCase("Vienna") || city.equalsIgnoreCase("Wien"),
                "City should be Vienna");
    }

    @Test
    @DisplayName("Reverse geocoding with radius constraint in Budapest")
    void testReverseWithRadius() {
        log.info("Initiating reverse geocoding in Budapest with radius constraint");

        PhotonResponse response = photonClient.reverse(
                47.4979,
                19.0402,
                10,
                "en",
                200.0,
                null
        );

        assertNotNull(response);
        assertFalse(response.getFeatures().isEmpty(), "Expected results within radius");
        log.info("Reverse geocoding with radius returned {} features", response.getFeatures().size());
    }

    @Test
    @DisplayName("Reverse geocoding with distance sorting enabled in Vienna")
    void testReverseWithDistanceSort() {
        log.info("Initiating reverse geocoding in Vienna with distance sorting");

        PhotonResponse response = photonClient.reverse(
                48.2082,
                16.3738,
                10,
                "en",
                300.0,
                true
        );

        assertNotNull(response);
        assertFalse(response.getFeatures().isEmpty(), "Expected sorted reverse results");
        log.info("Distance-sorted reverse geocoding returned {} features", response.getFeatures().size());
    }

    @Test
    @DisplayName("Complex reverse geocoding scenario in Hungary near Austria border")
    void testComplexReverseScenario() {
        log.info("Initiating complex reverse geocoding near Austria-Hungary border");

        PhotonResponse response = photonClient.reverse(
                47.8040,
                16.8417,
                15,
                "en",
                500.0,
                true
        );

        assertNotNull(response);
        assertFalse(response.getFeatures().isEmpty(), "Expected at least one reverse result");

        log.info("Complex reverse scenario returned {} features", response.getFeatures().size());

        response.getFeatures().forEach(feature ->
                log.info("Feature details: [Name: {}, City: {}, Street: {}, House Number: {}]",
                        feature.getProperties().getName(),
                        feature.getProperties().getCity(),
                        feature.getProperties().getStreet(),
                        feature.getProperties().getHousenumber())
        );
    }
}
