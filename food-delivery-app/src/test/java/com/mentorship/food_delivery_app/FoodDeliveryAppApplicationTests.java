package com.mentorship.food_delivery_app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;          // ✅ ADD THIS IMPORT
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@ActiveProfiles("test")
@SpringBootTest
class FoodDeliveryAppApplicationTests {
	@Autowired
	private DataSource dataSource;

	@Test
	void contextLoads() {
	}
/*	@Test
	void printAllTables() throws Exception {
		try (Connection conn = dataSource.getConnection()) {
			ResultSet rs = conn.getMetaData().getTables(
					null, null, "%", new String[]{"TABLE"}
			);

			System.out.println("========= TABLES IN DATABASE =========");
			boolean hasTables = false;
			while (rs.next()) {
				hasTables = true;
				System.out.println("✅ " + rs.getString("TABLE_NAME"));
			}
			if (!hasTables) {
				System.out.println("❌ NO TABLES FOUND!");
			}
			System.out.println("======================================");
		}
	}
*/
	@Test
	void printDataSourceUrl() throws Exception {
		System.out.println("=============================");
		System.out.println("DataSource URL: " +
				dataSource.getConnection().getMetaData().getURL());
		System.out.println("=============================");
	}

	@Test
	void printFlywayHistory() throws Exception {
		try (Connection conn = dataSource.getConnection();
			 Statement stmt = conn.createStatement()) {

			System.out.println("======= FLYWAY HISTORY =======");
			try {
				// ✅ All lowercase - table and column names
				ResultSet rs = stmt.executeQuery(
						"SELECT \"version\", \"description\", \"success\" " +
								"FROM \"flyway_schema_history\""
				);
				while (rs.next()) {
					System.out.println(
							"Version: "    + rs.getString("version") +
									" | Script: "  + rs.getString("description") +
									" | Success: " + rs.getBoolean("success")
					);
				}
			} catch (Exception e) {
				System.out.println("❌ Error: " + e.getMessage());
			}
			System.out.println("==============================");

			// ✅ Check tables at same time
			ResultSet tables = conn.getMetaData().getTables(
					null, null, "%", new String[]{"TABLE"}
			);
			System.out.println("======= TABLES =======");
			boolean found = false;
			while (tables.next()) {
				found = true;
				System.out.println("✅ " + tables.getString("TABLE_NAME"));
			}
			if (!found) System.out.println("❌ NO TABLES FOUND!");
			System.out.println("======================");
		}
	}
	@Test
	void showWorkingDirectory() {
		System.out.println(
				Paths.get("").toAbsolutePath().normalize()
		);
	}

}
