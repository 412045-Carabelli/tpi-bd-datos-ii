package org.datosii.tpidatosii.service;

import org.datosii.tpidatosii.model.Hashtag;
import org.datosii.tpidatosii.repository.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashtagService {

    @Autowired
    private HashtagRepository hashtagRepository;

    public List<Hashtag> getTrendingHashtags() {
        return hashtagRepository.findTrendingHashtags();
    }
}