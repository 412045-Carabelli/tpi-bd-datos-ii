package org.datosii.tpidatosii.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Node
public class Hashtag {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int usageCount = 0;

    @Relationship(type = "TAGGED_WITH", direction = Relationship.Direction.INCOMING)
    @JsonIgnore
    private Set<Post> posts = new HashSet<>();

    public Hashtag() {}

    public Hashtag(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getUsageCount() { return usageCount; }
    public void setUsageCount(int usageCount) { this.usageCount = usageCount; }

    public Set<Post> getPosts() { return posts; }
    public void setPosts(Set<Post> posts) { this.posts = posts; }
}