package com.codeit.test.url;

import com.codeit.test.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShortUrlService {

    private static final int MAX_GENERATION_ATTEMPTS = 5;

    private final ShortUrlRepository shortUrlRepository;
    private final ShortCodeGenerator shortCodeGenerator;

    @Value("${app.base-url}")
    private String baseUrl;

    /**
     * Creates (or returns the existing) short URL for the given long URL.
     * Requirement 3: the same long URL always maps to the same short code.
     */
    public ShortenResponse shorten(ShortenRequest request) {
        String longUrl = request.getLongUrl();

        ShortUrl shortUrl = shortUrlRepository.findByLongUrl(longUrl)
                .orElseGet(() -> create(longUrl));

        return new ShortenResponse(toShortUrl(shortUrl.getShortCode()));
    }

    /**
     * Resolves a short code to its original long URL for redirection.
     */
    public String resolve(String shortCode) {
        return shortUrlRepository.findByShortCode(shortCode)
                .map(ShortUrl::getLongUrl)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No URL found for short code: " + shortCode));
    }

    private ShortUrl create(String longUrl) {
        for (int attempt = 1; attempt <= MAX_GENERATION_ATTEMPTS; attempt++) {
            String code = shortCodeGenerator.generate();
            if (shortUrlRepository.existsByShortCode(code)) {
                continue;
            }
            try {
                ShortUrl entity = new ShortUrl(null, code, longUrl, Instant.now());
                return shortUrlRepository.save(entity);
            } catch (DuplicateKeyException e) {
                // Concurrent insert won the unique index race; retry with a new code.
                log.warn("Short code collision on '{}' (attempt {}), retrying", code, attempt);
            }
        }
        throw new IllegalStateException(
                "Failed to generate a unique short code after " + MAX_GENERATION_ATTEMPTS + " attempts");
    }

    private String toShortUrl(String shortCode) {
        return baseUrl + "/" + shortCode;
    }
}
