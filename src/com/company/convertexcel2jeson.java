package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ConvertExcel2Json
{
    public static void main(String[] args) {
        // Step 1: Read Excel File into Java List Objects
        List students = readExcelFile("C:\\Users\\yogesh\\Desktop\\FileHandeling\\StudentExcel.xlsx");

        // Step 2: Write Java List Objects to JSON File
        writeObjects2JsonFile(students, "C:\\Users\\yogesh\\Desktop\\FileHandeling\\Student.json");

        System.out.println("Done");
    }
    private static List readExcelFile(String filePath)
    {
        try
        {
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheet("Student1");
            Iterator rows = sheet.iterator();
            List lstStudents = new ArrayList();
            int rowNumber = 0;
            while (rows.hasNext())
            {
                Row currentRow = (Row) rows.next();
                // skip header
                if(rowNumber == 0)
                {
                    rowNumber++;
                    continue;
                }

                Iterator cellsInRow = currentRow.iterator();
                Student sust = new Student();
                int cellIndex = 0;
                while (cellsInRow.hasNext())
                {
                    Cell currentCell = (Cell) cellsInRow.next();

                    if(cellIndex==0)
                    {
                        // Student Name
                        sust.setName(currentCell.getStringCellValue());
                    } else if(cellIndex==1)
                    {
                        // Student Age
                        sust.setAge((int) currentCell.getNumericCellValue());
                    } else if(cellIndex==2)
                    {
                        // Student TotalMarks
                        sust.setTotalMarks((int) currentCell.getNumericCellValue());
                    }
                    cellIndex++;
                }

                lstStudents.add(sust);
            }

            // Close WorkBook
            workbook.close();

            return lstStudents;
        } catch (IOException e)
        {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }
    private static void writeObjects2JsonFile(List student, String pathFile) {
        ObjectMapper mapper = new ObjectMapper();

        File file = new File(pathFile);
        try {
            // Serialize Java object info JSON file.
//            mapper.writeValue(file, customers);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, student);
//            mapper.writer(PrettyPrinter.)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}