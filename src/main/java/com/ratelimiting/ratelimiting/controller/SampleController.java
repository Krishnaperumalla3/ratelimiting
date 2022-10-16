package com.ratelimiting.ratelimiting.controller;

import com.ratelimiting.ratelimiting.dto.AreaV1;
import com.ratelimiting.ratelimiting.dto.RectangleDimensionsV1;
import com.ratelimiting.ratelimiting.dto.TriangleDimensionsV1;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
//@RequestMapping(value = "/api/v1/area", consumes = MediaType.APPLICATION_JSON_VALUE)

public class SampleController {

    private final Bucket bucket;
    public SampleController() {
        Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }
    @PostMapping(value = "/rectangle")
    public ResponseEntity<AreaV1> rectangle(@RequestBody RectangleDimensionsV1 dimensions) {
        if (bucket.tryConsume(1)) {
            System.out.println("Dimensions " + dimensions.getWidth());
            return ResponseEntity.ok(new AreaV1("rectangle", dimensions.getLength() * dimensions.getWidth()));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PostMapping(value = "/triangle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AreaV1> triangle(@RequestBody TriangleDimensionsV1 dimensions) {
        return ResponseEntity.ok(new AreaV1("triangle", 0.5d * dimensions.getHeight() * dimensions.getBase()));
    }


}
