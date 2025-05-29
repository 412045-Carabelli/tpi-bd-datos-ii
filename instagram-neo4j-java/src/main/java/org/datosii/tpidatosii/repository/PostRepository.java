package org.datosii.tpidatosii.repository;

import org.datosii.tpidatosii.model.Post;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends Neo4jRepository<Post, Long> {

    @Query("MATCH (p:Post)<-[:POSTED]-(u:User {id: $userId}) " +
            "RETURN p ORDER BY p.createdAt DESC")
    List<Post> findByAuthorId(@Param("userId") Long userId);

    @Query("MATCH (p:Post)<-[:POSTED]-(u:User) " +
            "RETURN p ORDER BY p.createdAt DESC LIMIT 20")
    List<Post> findRecentPosts();

    @Query("MATCH (u:User {id: $userId}), (p:Post {id: $postId}) " +
            "CREATE (u)-[:LIKES]->(p) " +
            "SET p.likesCount = p.likesCount + 1")
    void likePost(@Param("userId") Long userId, @Param("postId") Long postId);

    @Query("MATCH (u:User {id: $userId})-[r:LIKES]->(p:Post {id: $postId}) " +
            "DELETE r " +
            "SET p.likesCount = p.likesCount - 1")
    void unlikePost(@Param("userId") Long userId, @Param("postId") Long postId);

    @Query("MATCH (u:User {id: $userId})-[:FOLLOWS]->(following)-[:POSTED]->(p:Post) " +
            "RETURN p ORDER BY p.createdAt DESC LIMIT 20")
    List<Post> findFeedForUser(@Param("userId") Long userId);

    @Query("MATCH (u:User {id: $userId})-[:LIKES]->(p1:Post)-[:TAGGED_WITH]->(h:Hashtag)" +
            "<-[:TAGGED_WITH]-(p2:Post) " +
            "WHERE NOT (u)-[:LIKES]->(p2) AND p1.id <> p2.id " +
            "WITH p2, count(h) as commonHashtags " +
            "ORDER BY commonHashtags DESC " +
            "LIMIT 10 " +
            "RETURN p2")
    List<Post> findRecommendedPostsByHashtags(@Param("userId") Long userId);

    @Query("MATCH (u:User {id: $userId})-[:LIKES]->(p1:Post)<-[:LIKES]-(other:User)" +
            "-[:LIKES]->(p2:Post) " +
            "WHERE NOT (u)-[:LIKES]->(p2) AND p1.id <> p2.id " +
            "WITH p2, count(other) as commonUsers " +
            "ORDER BY commonUsers DESC " +
            "LIMIT 10 " +
            "RETURN p2")
    List<Post> findRecommendedPostsByUserAffinity(@Param("userId") Long userId);
}