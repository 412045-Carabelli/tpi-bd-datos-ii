package org.datosii.tpidatosii.repository;

import org.datosii.tpidatosii.model.Hashtag;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashtagRepository extends Neo4jRepository<Hashtag, Long> {

    Optional<Hashtag> findByName(String name);

    @Query("MATCH (h:Hashtag) RETURN h ORDER BY h.usageCount DESC LIMIT 10")
    List<Hashtag> findTrendingHashtags();

    @Query("MATCH (h:Hashtag {name: $name}) SET h.usageCount = h.usageCount + 1")
    void incrementUsageCount(@Param("name") String name);
}