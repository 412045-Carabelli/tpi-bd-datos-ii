package org.datosii.tpidatosii.repository;

import org.datosii.tpidatosii.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query("MATCH (u:User {id: $userId})-[:FOLLOWS]->(following) RETURN following")
    List<User> findFollowing(@Param("userId") Long userId);

    @Query("MATCH (u:User {id: $userId})<-[:FOLLOWS]-(followers) RETURN followers")
    List<User> findFollowers(@Param("userId") Long userId);

    @Query("MATCH (u1:User {id: $userId1}), (u2:User {id: $userId2}) " +
            "CREATE (u1)-[:FOLLOWS]->(u2)")
    void followUser(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    @Query("MATCH (u1:User {id: $userId1})-[r:FOLLOWS]->(u2:User {id: $userId2}) DELETE r")
    void unfollowUser(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    @Query("MATCH (u:User {id: $userId})-[:LIKES]->(p:Post)<-[:LIKES]-(other:User) " +
            "WHERE other.id <> $userId " +
            "WITH other, count(p) as commonLikes " +
            "ORDER BY commonLikes DESC " +
            "LIMIT 10 " +
            "RETURN other")
    List<User> findUsersByCommonLikes(@Param("userId") Long userId);
}