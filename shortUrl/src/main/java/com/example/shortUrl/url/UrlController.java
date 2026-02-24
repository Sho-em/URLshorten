package com.example.shortUrl.url;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/web")
public class UrlController {
    private final UrlService urlService;
    private final UrlRepository urlRepository;


    public UrlController(UrlService urlService, UrlRepository urlRepository) {
        this.urlService = urlService;

        this.urlRepository = urlRepository;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> tinyUrl(@RequestBody String originalUrl){
        String shortUrl = urlService.tinyUrl(originalUrl);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(shortUrl);
    }

    @GetMapping("/{tinyUrl}")
    public ResponseEntity<UrlEntity> getOriginalUrl(@PathVariable String tinyUrl){
        UrlEntity originalUrl = urlService.getOriginalUrl(tinyUrl);
        return ResponseEntity.ok(originalUrl);
    }

    @GetMapping("/url")
    public List<UrlEntity> getAllUrl(){
        return  urlService.getAllUrl();
    }



    @PutMapping("/{tinyUrl}")
    public ResponseEntity<UrlEntity> updateUrl(@PathVariable String tinyUrl, @RequestBody String newUrl){
        urlRepository.findByShortCode(tinyUrl);
        UrlEntity newURL = urlService.updateUrl(newUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(newURL);
    }

    @DeleteMapping("/{id}")
    public void deleteUrl(@PathVariable Long id){
         urlService.deleteUrl(id);
    }

}
