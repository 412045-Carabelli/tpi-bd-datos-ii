package org.datosii.tpidatosii.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;

@Node
public class Comment {
    @Id @GeneratedValue
    private Long id;

    private String content;
    private LocalDateTime createdAt;

    @Relationship(type = "COMMENTED_BY", direction = Relationship.Direction.OUTGOING)
    private User author;

    @Relationship(type = "COMMENTED_ON", direction = Relationship.Direction.OUTGOING)
    private Post post;

    public Comment() {
        this.createdAt = LocalDateTime.now();
    }

    public Comment(String content, User author, Post post) {
        this();
        this.content = content;
        this.author = author;
        this.post = post;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }
}
