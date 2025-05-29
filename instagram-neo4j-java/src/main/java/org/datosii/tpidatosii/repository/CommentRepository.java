package org.datosii.tpidatosii.repository;

import org.datosii.tpidatosii.model.Comment;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends Neo4jRepository<Comment, Long> {

    @Query("MATCH (c:Comment)-[:COMMENTED_ON]->(p:Post {id: $postId}) " +
            "RETURN c ORDER BY c.createdAt DESC")
    List<Comment> findByPostId(@Param("postId") Long postId);
}
