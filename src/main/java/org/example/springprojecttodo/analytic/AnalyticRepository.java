package org.example.springprojecttodo.analytic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class AnalyticRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public AnalyticRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long countCompletedTasks() {
        return jdbcTemplate.queryForObject("SELECT COUNT(t) FROM Task t WHERE t.completed = true",
                Map.of(), Long.class);
    }

    public SqlRowSet showInfoByClient() {
        final String query = """
                    SELECT
                        c.id,
                        c.name || ' (' || c.email || ')' as fullName,
                        count(t.id) as taskCount,
                        count(*) FILTER (WHERE t.completed = true) as taskCompletedCount,
                        count(*) FILTER (WHERE t.completed = false) as taskNotCompletedCount
                    FROM client c
                    JOIN task t ON c.id = t.client_id
                    GROUP BY c.id
                """;

        return jdbcTemplate.queryForRowSet(query, Map.of());
    }

}
