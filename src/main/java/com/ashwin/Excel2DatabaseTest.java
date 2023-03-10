package com.ashwin.controller;
 
import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
 
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
 

public class Excel2DatabaseTest {
 
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/employeedetails";
        String username = "empdemo";
        String password = "password";
 
        String excelFilePath = "F:\\APK\\Employeedetails.xlsx";
 
        int batchSize = 20;
 
        Connection connection = null;
 
        try {
            long start = System.currentTimeMillis();
             
            FileInputStream inputStream = new FileInputStream(excelFilePath);
 
            Workbook workbook = new XSSFWorkbook(inputStream);
 
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = firstSheet.iterator();
 
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);
  
            String sql = "INSERT INTO `empdetails` (`empid`, `name`, `designation`) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);    
             
            int count = 0;
             
            rowIterator.next(); // skip the header row
             
            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
 
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
 
                    int columnIndex = nextCell.getColumnIndex();
 
                    switch (columnIndex) {
                    case 0:
                        int empid = (int) nextCell.getNumericCellValue();
                        statement.setInt(1, empid);
                        break;
                    case 1:
                    	 String name = nextCell.getStringCellValue();
                         statement.setString(2, name);
                         break;
                    case 2:
                    	 String designation = nextCell.getStringCellValue();
                         statement.setString(3, designation);
                         break;
                    }
 
                }
                 
                statement.addBatch();
                 
                if (count % batchSize == 0) {
                    statement.executeBatch();
                }              
 
            }
 
            workbook.close();
             
            // execute the remaining queries
            statement.executeBatch();
  
            connection.commit();
            connection.close();
             
            long end = System.currentTimeMillis();
            System.out.printf("Import done in %d ms\n", (end - start));
             
        } catch (IOException ex1) {
            System.out.println("Error reading file");
            ex1.printStackTrace();
        } catch (SQLException ex2) {
            System.out.println("Database error");
            ex2.printStackTrace();
        }
 
    }
}