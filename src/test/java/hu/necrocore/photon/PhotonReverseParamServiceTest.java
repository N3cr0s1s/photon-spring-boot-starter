package hu.necrocore.photon;

import hu.necrocore.photon.domain.GeoPoint;
import hu.necrocore.photon.domain.PhotonReverseRequest;
import hu.necrocore.photon.dto.PhotonResponse;
import hu.necrocore.photon.service.PhotonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(classes = PhotonReverseParamServiceTest.TestConfig.class)
class PhotonReverseParamServiceTest {

    @Configuration
    @EnableAutoConfiguration
    static class TestConfig {
    }

    @Autowired
    private PhotonService photonService;

    @Test
    @DisplayName("Reverse geocoding with basic latitude and longitude in Budapest")
    void testReverseBasicBudapest() {
        log.info("Initiating basic reverse geocoding in Budapest");

        var response = photonService.reverse(PhotonReverseRequest.builder(new GeoPoint(47.4979, 19.0402)).build());

        assertNotNull(response);
        assertFalse(response.getFeatures().isEmpty(), "Expected at least one reverse result");
        log.info("Reverse geocoding completed with {} features", response.getFeatures().size());
    }

    @Test
    @DisplayName("Reverse geocoding with limit and language in Vienna")
    void testReverseWithLimitAndLangVienna() {
        log.info("Initiating reverse geocoding in Vienna with limit and language");

        var response = photonService.reverse(PhotonReverseRequest.builder(new GeoPoint(48.2082, 16.3738))
                .limit(5)
                .language("en")
                .build());

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

        var response = photonService.reverse(PhotonReverseRequest.builder(new GeoPoint(47.4979, 19.0402))
                .limit(10)
                .language("en")
                .radius(200.0)
                .build());

        assertNotNull(response);
        assertFalse(response.getFeatures().isEmpty(), "Expected results within radius");
        log.info("Reverse geocoding with radius returned {} features", response.getFeatures().size());
    }

    @Test
    @DisplayName("Reverse geocoding with distance sorting enabled in Vienna")
    void testReverseWithDistanceSort() {
        log.info("Initiating reverse geocoding in Vienna with distance sorting");

        var response = photonService.reverse(PhotonReverseRequest.builder(new GeoPoint(48.2082, 16.3738))
                .limit(10)
                .language("en")
                .radius(300.0)
                .distanceSort(true)
                .build());

        assertNotNull(response);
        assertFalse(response.getFeatures().isEmpty(), "Expected sorted reverse results");
        log.info("Distance-sorted reverse geocoding returned {} features", response.getFeatures().size());
    }

    @Test
    @DisplayName("Complex reverse geocoding scenario in Hungary near Austria border")
    void testComplexReverseScenario() {
        log.info("Initiating complex reverse geocoding near Austria-Hungary border");

        var response = photonService.reverse(PhotonReverseRequest.builder(new GeoPoint(47.8040, 16.8417))
                .limit(15)
                .language("en")
                .radius(500.0)
                .distanceSort(true)
                .build());

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
