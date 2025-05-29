package org.datosii.tpidatosii.controller;

import org.datosii.tpidatosii.model.Hashtag;
import org.datosii.tpidatosii.service.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hashtags")
@CrossOrigin(origins = "*")
public class HashtagController {

    @Autowired
    private HashtagService hashtagService;

    @GetMapping("/trending")
    public ResponseEntity<List<Hashtag>> getTrendingHashtags() {
        List<Hashtag> trendingHashtags = hashtagService.getTrendingHashtags();
        return ResponseEntity.ok(trendingHashtags);
    }
}

