package hu.necrocore.photon.client;

import hu.necrocore.photon.dto.PhotonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface PhotonClient {

    @GetMapping("/api")
    PhotonResponse search(
            @RequestParam("q") String query,
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "limit", required = false) Integer limit
    );

    @GetMapping("/reverse")
    PhotonResponse reverse(
            @RequestParam("lat") Double lat,
            @RequestParam("lon") Double lon
    );
}