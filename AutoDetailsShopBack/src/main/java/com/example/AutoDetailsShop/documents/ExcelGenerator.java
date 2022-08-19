package com.example.AutoDetailsShop.documents;

import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.service.OfferService;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class ExcelGenerator {

    public static CompletableFuture<ByteArrayInputStream> generateExcelForOffers(OfferService offerService) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Offers");
        List<Offer> offers = offerService.getAll();
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        createCell(sheet, headerRow, 0, "Id", headerStyle);
        createCell(sheet, headerRow, 1, "Detail name", headerStyle);
        createCell(sheet, headerRow, 2, "Car brand name", headerStyle);
        createCell(sheet, headerRow, 3, "Car model name", headerStyle);
        createCell(sheet, headerRow, 4, "Price", headerStyle);
        int rowCount = 1;
        CellStyle bodyStyle = workbook.createCellStyle();
        HSSFFont bodyFont = workbook.createFont();
        bodyStyle.setFont(bodyFont);
        for(Offer offer : offers){
            Row bodyRow = sheet.createRow(rowCount ++);
            int columnCount = 0;

            createCell(sheet, bodyRow, columnCount ++, offer.getId(), bodyStyle);
            createCell(sheet, bodyRow, columnCount ++, offer.getDetail().getDetailName(), bodyStyle);
            createCell(sheet, bodyRow, columnCount ++, offer.getCarBrand().getCarBrandName(), bodyStyle);
            createCell(sheet, bodyRow, columnCount ++, offer.getCarModel().getCarModelName(), bodyStyle);
            createCell(sheet, bodyRow, columnCount, offer.getPrice(), bodyStyle);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return CompletableFuture.completedFuture(new ByteArrayInputStream(out.toByteArray()));
    }

    private static void createCell(HSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style){
        Cell cell = row.createCell(columnCount);
        if(value instanceof Long){
            cell.setCellValue(value.toString());
        }else if(value instanceof BigDecimal){
            cell.setCellValue(((BigDecimal) value).doubleValue());
        }else{
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
        sheet.autoSizeColumn(columnCount);
    }
}
