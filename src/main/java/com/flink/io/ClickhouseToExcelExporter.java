package com.flink.io;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ClickhouseToExcelExporter {
    public static void main(String[] args) throws Exception {
        // Connect to Clickhouse database
        String url = "jdbc:clickhouse://192.168.139.106:8123/default";
        String user = "default";
        String password = "qwer123456";
        Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();

        // Execute SQL query to get data from Clickhouse
        String sql = "SELECT * FROM bus_MergeAlarmMessage limit 1000";
        ResultSet rs = stmt.executeQuery(sql);

        // Create Excel workbook and sheet
        XSSFWorkbook workbook = new XSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Data");

        // Write data to Excel sheet
        int rownum = 0;
        while (rs.next()) {
            Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                Cell cell = row.createCell(cellnum++);
                cell.setCellValue(rs.getString(i));
            }
        }

        // Save Excel file
        FileOutputStream out = new FileOutputStream("D:/data.xlsx");
        workbook.write(out);

        out.close();

        // Close database connection
        rs.close();
        stmt.close();
        conn.close();
    }
}
