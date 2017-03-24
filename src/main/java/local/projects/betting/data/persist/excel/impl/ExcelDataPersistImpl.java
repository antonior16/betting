package local.projects.betting.data.persist.excel.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.projects.betting.api.DataPersist;
import local.projects.betting.data.entry.api.football.model.Fixture;
import local.projects.betting.model.Odds;
import local.projects.betting.model.Result;

public class ExcelDataPersistImpl implements DataPersist {
  private static final Logger LOGGER = LoggerFactory.getLogger(ExcelDataPersistImpl.class);
  
  // Populate Excel Constants
  private String fileName;
  private String sheetName;
  
  public ExcelDataPersistImpl() {
  }
  
  public ExcelDataPersistImpl(String fileName) {
    this.fileName = fileName;
  }
  
  public ExcelDataPersistImpl(String fileName, String sheetName) {
    this.fileName = fileName;
    this.sheetName = sheetName;
  }
  
  public String getFileName() {
    return fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public String getSheetName() {
    return sheetName;
  }
  
  public void setSheetName(String sheetName) {
    this.sheetName = sheetName;
  }
  
  @Override
  public void persistFixtures(Map<Integer, Fixture> fixtures) {
    // private void populateExcel() {
    XSSFWorkbook workbook;
    try {
      workbook = new XSSFWorkbook(new FileInputStream(fileName));
      
      // int index = workbook.getSheetIndex("Quote");
      // workbook.removeSheetAt(index);
      
      XSSFSheet sheet = workbook.getSheet("Fixtures");
      // Iterate over data and write to sheet
      Set<Integer> keyset = fixtures.keySet();
      int rownum = 0;
      for (Integer key : keyset) {
        Row row = sheet.createRow(rownum++);
        Fixture objArr = fixtures.get(key);
        try {
          // Saving only match having Odds
          
          LOGGER.debug(objArr.getHomeTeamName().getName() + " : " + objArr.getAwayTeamName().getName());
          Cell cell = row.createCell(0);
          cell.setCellValue(objArr.getDate().toLocaleString());
          
          cell = row.createCell(1);
          cell.setCellValue(objArr.getHomeTeamName().getName());
          
          cell = row.createCell(2);
          cell.setCellValue(objArr.getAwayTeamName().getName());
          
          // Getting Odds
          if (objArr.getOdds() != null && objArr.getStatus().equals("FINISHED")) {
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(
                workbook.getCreationHelper().createDataFormat().getFormat("#,##0.00"));
            
            cell = row.createCell(3);
            cell.setCellValue(objArr.getOdds().getHomeWin());
            cell.setCellStyle(cellStyle);
            
            cell = row.createCell(4);
            cell.setCellValue(objArr.getOdds().getDraw());
            cell.setCellStyle(cellStyle);
            
            cell = row.createCell(5);
            cell.setCellValue(objArr.getOdds().getAwayWin());
            cell.setCellStyle(cellStyle);
            
          }
          
          if (objArr.getStatus().equals("FINISHED")) {
            int goalsHomeTeam = objArr.getResult().getGoalsHomeTeam();
            int goalsAwayTeam = objArr.getResult().getGoalsAwayTeam();
            
            // Setting Scores
            cell = row.createCell(6);
            cell.setCellValue(goalsHomeTeam + ":" + goalsAwayTeam);
            
            cell = row.createCell(7);
            
            if (goalsHomeTeam > goalsAwayTeam) {
              cell.setCellValue("1");
            } else {
              cell.setCellValue("2");
            }
            
            if ((goalsHomeTeam - goalsAwayTeam) == 0) {
              cell.setCellValue("X");
            }
            
            // Setting Gol/NoGol
            cell = row.createCell(8);
            if (goalsHomeTeam > 0 & goalsAwayTeam > 0) {
              cell.setCellValue("GOL");
            } else {
              cell.setCellValue("NOGOL");
            }
            
            // Setting Under/Over
            cell = row.createCell(9);
            if (goalsHomeTeam + goalsAwayTeam >= 3) {
              cell.setCellValue("OVER");
            } else {
              cell.setCellValue("UNDER");
            }
          }
          
        } catch (Exception e) {
          LOGGER.error("Errore -----> : " + objArr.getHomeTeamName().getName() + " : "
              + objArr.getAwayTeamName().getName());
          LOGGER.error(e.getCause().getMessage());
        }
      }
      // Writing in Excel
      try {
        // Write the workbook in file system
        FileOutputStream out = new FileOutputStream(fileName);
        workbook.write(out);
        out.close();
        LOGGER.info("Scores have been writed in: " + fileName);
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    LOGGER.info("Done");
  }
  
  public void persistOdds(Map<Integer, Odds> odds) {
    // private void populateExcel() {
    XSSFWorkbook workbook;
    try {
      workbook = new XSSFWorkbook(new FileInputStream(fileName));
      
      CellStyle cellStyle = workbook.createCellStyle();
      // int index = workbook.getSheetIndex("Quote");
      // workbook.removeSheetAt(index);
      int sheetIndex = workbook.getSheetIndex(sheetName);
      if (sheetIndex>0) {
    	  workbook.removeSheetAt(sheetIndex);
	}
      XSSFSheet sheet = workbook.createSheet(sheetName);
      // Iterate over data and write to sheet
      Set<Integer> keyset = odds.keySet();
      int rownum = 0;
      for (Integer key : keyset) {
        Row row = sheet.createRow(rownum++);
        Odds objArr = odds.get(key);
        try {
          // Saving only match having Odds
          
          LOGGER.debug(objArr.getHomeTeamName() + " : " + objArr.getAwayTeamName().getName());
          Cell cell = row.createCell(0);
          CreationHelper createHelper = workbook.getCreationHelper();
          cellStyle.setDataFormat(
              createHelper.createDataFormat().getFormat("d-m-yy"));
          cell.setCellStyle(cellStyle);
          if (objArr.getDate() == null) {
        	  DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        	  String oddsDate = df.format(new Date()).toString();
            cell.setCellValue(oddsDate);
          } else {
            cell.setCellValue(objArr.getDate().toLocaleString());
          }
          
          cell = row.createCell(1);
          cell.setCellValue(objArr.getHomeTeamName().getName());
          
          cell = row.createCell(2);
          cell.setCellValue(objArr.getAwayTeamName().getName());
          
          cellStyle.setDataFormat(
              workbook.getCreationHelper().createDataFormat().getFormat("#,##0.00"));
          
          cell = row.createCell(3);
          cell.setCellValue(objArr.getHomeWin());
          cell.setCellStyle(cellStyle);
          
          cell = row.createCell(4);
          cell.setCellValue(objArr.getDraw());
          cell.setCellStyle(cellStyle);
          
          cell = row.createCell(5);
          cell.setCellValue(objArr.getAwayWin());
          cell.setCellStyle(cellStyle);
          
          if (objArr.getUnder() != null) {
            cell = row.createCell(6);
            cell.setCellValue(objArr.getUnder());
            cell.setCellStyle(cellStyle);
          }
          
          if (objArr.getOver() != null) {
            cell = row.createCell(7);
            cell.setCellValue(objArr.getOver());
            cell.setCellStyle(cellStyle);
          }
          
          if (objArr.getGol() != null) {
            cell = row.createCell(8);
            cell.setCellValue(objArr.getGol());
            cell.setCellStyle(cellStyle);
          }
          
          if (objArr.getNoGol() != null) {
            cell = row.createCell(9);
            cell.setCellValue(objArr.getNoGol());
            cell.setCellStyle(cellStyle);
          }
          
        } catch (Exception e) {
          LOGGER.error(
              "Errore -----> : " + objArr.getHomeTeamName() + " : " + objArr.getAwayTeamName().getName());
          LOGGER.error(e.getCause().getMessage());
        }
      }
      // Writing in Excel
      try {
        // Write the workbook in file system
        FileOutputStream out = new FileOutputStream(fileName);
        workbook.write(out);
        out.close();
        LOGGER.info("Odds have been writed in: " + fileName);
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    LOGGER.info("Done");
  }
  
  @Override
  public void persistResults(Map<Integer, Result> results) {
    // private void populateExcel() {
    XSSFWorkbook workbook;
    try {
      workbook = new XSSFWorkbook(new FileInputStream(fileName));
      
      // int index = workbook.getSheetIndex("Quote");
      // workbook.removeSheetAt(index);
      int sheetIndex = workbook.getSheetIndex(sheetName);
      if (sheetIndex>0) {
    	  workbook.removeSheetAt(sheetIndex);
	}
      XSSFSheet sheet = workbook.createSheet(sheetName);
      // Iterate over data and write to sheet
      Set<Integer> keyset = results.keySet();
      int rownum = 0;
      for (Integer key : keyset) {
        Row row = sheet.createRow(rownum++);
        Result objArr = results.get(key);
        try {
          // Saving only match having Odds
          
          LOGGER.debug(objArr.getHomeTeamName().getName() + " : " + objArr.getAwayTeamName().getName());
          Cell cell = row.createCell(0);
          
          CreationHelper createHelper = workbook.getCreationHelper();
          CellStyle cellStyle = workbook.createCellStyle();
          cellStyle.setDataFormat(
              createHelper.createDataFormat().getFormat("d-m-yy"));
          cell.setCellStyle(cellStyle);
          cell.setCellValue(objArr.getDate().toLocaleString());
          
          cell = row.createCell(1);
          cell.setCellValue(objArr.getHomeTeamName().getName());
          
          cell = row.createCell(2);
          cell.setCellValue(objArr.getAwayTeamName().getName());
          
          int goalsHomeTeam = objArr.getGoalsHomeTeam();
          int goalsAwayTeam = objArr.getGoalsAwayTeam();
          
          // Setting Scores
          cell = row.createCell(3);
          cell.setCellValue(goalsHomeTeam + ":" + goalsAwayTeam);
          
          cell = row.createCell(4);
          
          if (goalsHomeTeam > goalsAwayTeam) {
            cell.setCellValue("1");
          } else {
            cell.setCellValue("2");
          }
          
          if ((goalsHomeTeam - goalsAwayTeam) == 0) {
            cell.setCellValue("X");
          }
          
          // Setting Gol/NoGol
          cell = row.createCell(5);
          if (goalsHomeTeam > 0 & goalsAwayTeam > 0) {
            cell.setCellValue("GOL");
          } else {
            cell.setCellValue("NOGOL");
          }
          
          // Setting Under/Over
          cell = row.createCell(6);
          if (goalsHomeTeam + goalsAwayTeam >= 3) {
            cell.setCellValue("OVER");
          } else {
            cell.setCellValue("UNDER");
          }
          
        } catch (Exception e) {
          LOGGER.error(
              "Errore -----> : " + objArr.getHomeTeamName() + " : " + objArr.getAwayTeamName().getName());
          LOGGER.error(e.getCause().getMessage());
        }
      }
      // Writing in Excel
      try {
        // Write the workbook in file system
        FileOutputStream out = new FileOutputStream(fileName);
        workbook.write(out);
        out.close();
        LOGGER.info("Scores have been writed in: " + fileName);
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    LOGGER.info("Done");
  }
  
}
