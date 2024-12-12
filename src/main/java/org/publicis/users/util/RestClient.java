package org.publicis.users.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class RestClient {

    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);

    private RestTemplate restTemplate;

    public String fetchData(String url) {
        logger.info("Fetching data from URL: {}", url);
        restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
