package hu.necrocore.photon.client;

import hu.necrocore.photon.dto.PhotonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface PhotonClient {

    @GetMapping("/api")
    PhotonResponse search(
            @RequestParam("q") String query,
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "lon", required = false) Double lon,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "bbox", required = false) String bbox,
            @RequestParam(value = "osm_tag", required = false) String[] osmTags,
            @RequestParam Map<String, Object> otherParams
    );

    @GetMapping("/reverse")
    PhotonResponse reverse(
            @RequestParam("lat") Double lat,
            @RequestParam("lon") Double lon,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "radius", required = false) Double radius,
            @RequestParam(value = "distance_sort", required = false) Boolean distanceSort
    );
}