package org.datosii.tpidatosii.service;

import org.datosii.tpidatosii.dto.PostCreateDto;
import org.datosii.tpidatosii.model.Hashtag;
import org.datosii.tpidatosii.model.Post;
import org.datosii.tpidatosii.model.User;
import org.datosii.tpidatosii.repository.HashtagRepository;
import org.datosii.tpidatosii.repository.PostRepository;
import org.datosii.tpidatosii.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashtagRepository hashtagRepository;

    @Transactional
    public Post createPost(PostCreateDto postDto) {
        Optional<User> author = userRepository.findById(postDto.getAuthorId());
        if (!author.isPresent()) {
            throw new RuntimeException("User not found");
        }

        Post post = new Post(postDto.getContent(), author.get());
        post.setImageUrl(postDto.getImageUrl());

        // Process hashtags
        if (postDto.getHashtags() != null && !postDto.getHashtags().isEmpty()) {
            Set<Hashtag> hashtags = new HashSet<>();
            for (String hashtagName : postDto.getHashtags()) {
                Hashtag hashtag = hashtagRepository.findByName(hashtagName)
                        .orElse(new Hashtag(hashtagName));
                hashtag = hashtagRepository.save(hashtag);
                hashtagRepository.incrementUsageCount(hashtagName);
                hashtags.add(hashtag);
            }
            post.setHashtags(hashtags);
        }

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findRecentPosts();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByAuthorId(userId);
    }

    public List<Post> getFeedForUser(Long userId) {
        return postRepository.findFeedForUser(userId);
    }

    public void likePost(Long userId, Long postId) {
        postRepository.likePost(userId, postId);
    }

    public void unlikePost(Long userId, Long postId) {
        postRepository.unlikePost(userId, postId);
    }

    public List<Post> getRecommendedPostsByHashtags(Long userId) {
        return postRepository.findRecommendedPostsByHashtags(userId);
    }

    public List<Post> getRecommendedPostsByUserAffinity(Long userId) {
        return postRepository.findRecommendedPostsByUserAffinity(userId);
    }
}