package com.rikkei.ss12.b4;

import com.rikkei.ss12.b4.tool.SafeSqlExecutorMcpTool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SafeSqlExecutorMcpToolTest {

    @Autowired
    private SafeSqlExecutorMcpTool sqlTool;

    @Test
    @DisplayName("SafeSqlExecutorMcpTool rejects DROP TABLE and appends LIMIT 100 to SELECT")
    void testSqlDefenseAndLimitInjection() {
        Map<String, Object> rejectRes = sqlTool.executeSqlQuery("DROP TABLE accounts");
        assertEquals("REJECTED", rejectRes.get("status"));

        Map<String, Object> validRes = sqlTool.executeSqlQuery("SELECT * FROM accounts");
        assertEquals("SUCCESS", validRes.get("status"));
        assertTrue(((String) validRes.get("executedSql")).contains("LIMIT 100"));
    }
}
