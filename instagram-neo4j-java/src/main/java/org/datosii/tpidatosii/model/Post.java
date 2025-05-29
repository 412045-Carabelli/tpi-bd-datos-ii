package org.datosii.tpidatosii.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Node
public class Post {
    @Id @GeneratedValue
    private Long id;

    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private int likesCount = 0;
    private int commentsCount = 0;

    @Relationship(type = "POSTED", direction = Relationship.Direction.INCOMING)
    private User author;

    @Relationship(type = "LIKES", direction = Relationship.Direction.INCOMING)
    @JsonIgnore
    private Set<User> likedBy = new HashSet<>();

    @Relationship(type = "TAGGED_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<Hashtag> hashtags = new HashSet<>();

    @Relationship(type = "COMMENTED_ON", direction = Relationship.Direction.INCOMING)
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    public Post() {
        this.createdAt = LocalDateTime.now();
    }

    public Post(String content, User author) {
        this();
        this.content = content;
        this.author = author;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public int getLikesCount() { return likesCount; }
    public void setLikesCount(int likesCount) { this.likesCount = likesCount; }

    public int getCommentsCount() { return commentsCount; }
    public void setCommentsCount(int commentsCount) { this.commentsCount = commentsCount; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public Set<User> getLikedBy() { return likedBy; }
    public void setLikedBy(Set<User> likedBy) { this.likedBy = likedBy; }

    public Set<Hashtag> getHashtags() { return hashtags; }
    public void setHashtags(Set<Hashtag> hashtags) { this.hashtags = hashtags; }

    public Set<Comment> getComments() { return comments; }
    public void setComments(Set<Comment> comments) { this.comments = comments; }
}
