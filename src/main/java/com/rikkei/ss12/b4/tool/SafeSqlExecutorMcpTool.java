package com.rikkei.ss12.b4.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class SafeSqlExecutorMcpTool {

    private static final Logger log = LoggerFactory.getLogger(SafeSqlExecutorMcpTool.class);

    private static final Pattern FORBIDDEN_KEYWORDS = Pattern.compile(
            "\\b(DROP|DELETE|UPDATE|INSERT|ALTER|TRUNCATE|GRANT|REVOKE|EXEC|CREATE)\\b",
            Pattern.CASE_INSENSITIVE
    );

    public Map<String, Object> executeSqlQuery(String rawSql) {
        log.info("[MCP SQL TOOL] Evaluating incoming SQL query: '{}'", rawSql);

        if (rawSql == null || rawSql.isBlank()) {
            return Map.of("status", "ERROR", "message", "SQL Query cannot be empty.");
        }

        // 1. Defense Layer: Reject Multiple Statements (';')
        if (rawSql.contains(";")) {
            log.warn("[SQL SECURITY VIOLATION] Multiple SQL statements detected!");
            return Map.of("status", "REJECTED", "error", "SECURITY_VIOLATION: Multiple statements separated by ';' are strictly forbidden.");
        }

        // 2. Defense Layer: Reject Destructive Non-READ Keywords
        if (FORBIDDEN_KEYWORDS.matcher(rawSql).find()) {
            log.warn("[SQL SECURITY VIOLATION] Destructive SQL keyword detected in query!");
            return Map.of("status", "REJECTED", "error", "SECURITY_VIOLATION: Only READ-ONLY (SELECT) queries are allowed.");
        }

        // 3. Defense Layer: Force LIMIT 100 to prevent Token Context Window Overflow
        String safeSql = rawSql.trim();
        if (!safeSql.toUpperCase().contains("LIMIT")) {
            safeSql = safeSql + " LIMIT 100";
            log.info("[TOKEN OVERFLOW GUARD] Injected 'LIMIT 100' to query context window.");
        }

        log.info("[SQL EXECUTION SUCCESS] Executing sanitized SQL: {}", safeSql);
        return Map.of(
                "status", "SUCCESS",
                "executedSql", safeSql,
                "recordCount", 2,
                "data", List.of(
                        Map.of("id", 1, "account_number", "1903****1111", "balance", 5000000),
                        Map.of("id", 2, "account_number", "1903****2222", "balance", 12000000)
                )
        );
    }
}
