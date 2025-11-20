package com.trilhajusta.oracle;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Map;

@Repository
public class MatchOracleRepository {

    private final JdbcTemplate jdbcTemplate;

    public MatchOracleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public double scoreCompatibilidade(long usuarioId, long vagaId) {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_MATCH")
                .withFunctionName("FN_SCORE_COMPATIBILIDADE")
                .declareParameters(
                        new SqlOutParameter("return", Types.NUMERIC),
                        new SqlParameter("p_usuario_id", Types.NUMERIC),
                        new SqlParameter("p_vaga_id", Types.NUMERIC)
                );
        Map<String, Object> result = call.execute(usuarioId, vagaId);
        Object val = result.get("return");
        return val == null ? 0.0 : ((Number) val).doubleValue();
    }
}
