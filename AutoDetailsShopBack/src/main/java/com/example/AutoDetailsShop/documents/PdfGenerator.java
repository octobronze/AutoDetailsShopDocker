package com.example.AutoDetailsShop.documents;

import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.service.OfferService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public final class PdfGenerator {

    private PdfGenerator(){}

    public static ByteArrayInputStream generatePdfForOffers(OfferService offerService) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 3, 3, 3, 3});
        table.setSpacingBefore(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        PdfPCell headCell;
        headCell = new PdfPCell(new Phrase("Id", font));
        table.addCell(headCell);
        headCell = new PdfPCell(new Phrase("Detail name", font));
        table.addCell(headCell);
        headCell = new PdfPCell(new Phrase("Car brand name", font));
        table.addCell(headCell);
        headCell = new PdfPCell(new Phrase("Car model name", font));
        table.addCell(headCell);
        headCell = new PdfPCell(new Phrase("Price", font));
        table.addCell(headCell);
        List<Offer> offers = offerService.getAll();
        for(Offer offer : offers){
            PdfPCell bodyCell;
            bodyCell = new PdfPCell(new Phrase(offer.getId().toString()));
            table.addCell(bodyCell);
            bodyCell = new PdfPCell(new Phrase(offer.getDetail().getDetailName()));
            table.addCell(bodyCell);
            bodyCell = new PdfPCell(new Phrase(offer.getCarBrand().getCarBrandName()));
            table.addCell(bodyCell);
            bodyCell = new PdfPCell(new Phrase(offer.getCarModel().getCarModelName()));
            table.addCell(bodyCell);
            bodyCell = new PdfPCell(new Phrase(offer.getPrice().toString()));
            table.addCell(bodyCell);
        }
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);
        Paragraph paragraph = new Paragraph("Offers List", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(paragraph);
        document.add(table);
        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
