package com.rikkei.ss12.b4;

import com.rikkei.ss12.b4.tool.SafeSqlExecutorMcpTool;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final SafeSqlExecutorMcpTool sqlTool;

    public Application(SafeSqlExecutorMcpTool sqlTool) {
        this.sqlTool = sqlTool;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=================================================");
        System.out.println("   SAFE SQL MCP TOOL DEMONSTRATION - BÀI 4 (SS12)");
        System.out.println("=================================================");

        System.out.println("\n--- TEST 1: Valid SELECT without LIMIT ---");
        Map<String, Object> r1 = sqlTool.executeSqlQuery("SELECT * FROM accounts WHERE status = 'ACTIVE'");
        System.out.println(r1);

        System.out.println("\n--- TEST 2: Attack Attempt with DROP TABLE ---");
        Map<String, Object> r2 = sqlTool.executeSqlQuery("DROP TABLE accounts");
        System.out.println(r2);

        System.out.println("=================================================");
    }
}
