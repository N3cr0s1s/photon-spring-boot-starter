package hu.necrocore.photon;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import hu.necrocore.photon.client.PhotonClient;
import hu.necrocore.photon.config.PhotonConfig;
import hu.necrocore.photon.service.PhotonService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(PhotonConfig.class)
public class PhotonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PhotonClient photonClient(PhotonConfig properties) {
        return Feign.builder()
                .contract(new SpringMvcContract())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(PhotonClient.class, properties.getBaseUrl());
    }

    @Bean
    @ConditionalOnMissingBean
    public PhotonService photonService(PhotonClient photonClient) {
        return new PhotonService(photonClient);
    }
}