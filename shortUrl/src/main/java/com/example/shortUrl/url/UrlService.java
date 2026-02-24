package com.example.shortUrl.url;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class UrlService {

    private final UrlRepository urlRepo;

    public UrlService(UrlRepository urlRepo) {
        this.urlRepo = urlRepo;
    }

    public String tinyUrl(String originalUrl){
        String shortCode = generateShortUrl();
        UrlEntity url = new UrlEntity();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        url.setCreatedAt(LocalDateTime.now());
        url.setUpdatedAt(LocalDateTime.now());
        urlRepo.save(url);
        return "https://shorty.com/api/long/" + shortCode;
    }

    public UrlEntity getOriginalUrl(String shortCode){
        return urlRepo.findByShortCode(shortCode).orElseThrow(() -> new RuntimeException("URL not found"));

    }

    public List<UrlEntity> getAllUrl(){
        return urlRepo.findAll();
    }

    public UrlEntity updateUrl(String newUrl){
        String shortCode = generateShortUrl();
        UrlEntity url = new UrlEntity();
        url.setOriginalUrl(newUrl);
        url.setCreatedAt(LocalDateTime.now());
        url.setUpdatedAt(url.getUpdatedAt());
        url.setShortCode("https://newShorty.com/api/long/" + shortCode);
        return urlRepo.save(url);
    }

    public void deleteUrl(Long id){
        Boolean exist = urlRepo.existsById(id);
        if(!exist){
            throw new RuntimeException("URL not found");
        }
        urlRepo.deleteById(id);
    }

    public String generateShortUrl(){
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6; i++){
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return  sb.toString();
    }
}
