package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.awaitility.Awaitility.await;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


/**
 * if in properties useLocalApp=false, this service will upload .ipa file to server, otherwise local build can be user for local testing
 */
public class FileUploadService {
    private static final Logger logger = Logger.getLogger(FileUploadService.class.getName());
    private static final ConfigReader reader = new ConfigReader();

    @NoArgsConstructor
    static class Body { public String app_url;}

    public static String postFile(String url, File file) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        final HttpHeaders headers = new HttpHeaders() {{
            set(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE);
            setBasicAuth(reader.getUserName(), reader.getAccessKey());
            set("custom_id", "SampleApp");
            set(ACCEPT, APPLICATION_JSON_VALUE);
        }};

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new FileSystemResource(file));

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        String urlAddress;
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            await().atMost(20, TimeUnit.SECONDS).pollInterval(2, TimeUnit.SECONDS).until(() ->
                    Objects.nonNull(response.getBody()));
            logger.info("File successfully uploaded to: " + response.getBody() + " with status code: " + response.getStatusCodeValue());
            urlAddress = mapper.readValue(response.getBody(), Body.class).app_url;
        } catch (Exception e) {
            logger.info( "Exception: " + e.getMessage());
            urlAddress = "";
        }
        return urlAddress;
    }
}
