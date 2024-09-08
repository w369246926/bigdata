package com.flink.io;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import lombok.var;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportToExcel {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:clickhouse://192.168.139.106:8123/default";
        String username = "default";
        String password = "qwer123456";
        String query = "SELECT * FROM bus_MergeAlarmMessage limit 1000";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream("data1.xlsx")) {

            // Create a new sheet
            var sheet = workbook.createSheet("Data");

            // Create header row
            var headerRow = sheet.createRow(0);
            var metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Cell cell = headerRow.createCell(i - 1);
                cell.setCellValue(columnName);
            }

            // Create data rows
            int rowNumber = 1;
            while (rs.next()) {
                var dataRow = sheet.createRow(rowNumber++);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = rs.getObject(i);
                    Cell cell = dataRow.createCell(i - 1);
                    cell.setCellValue(value.toString());
                }
            }

            // Write the workbook to the output stream
            workbook.write(out);
            System.out.println("Data exported successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
