package com.frans.bcmanager.pdf;

import com.frans.bcmanager.model.DocumentLine;
import com.frans.bcmanager.model.Enterprise;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Map;

import static com.frans.bcmanager.pdf.DocumentConstants.FONT_DATE;
import static com.frans.bcmanager.pdf.DocumentConstants.FONT_DOCUMENT_CODE;
import static com.frans.bcmanager.pdf.DocumentConstants.FONT_GREET;
import static com.frans.bcmanager.pdf.DocumentConstants.FONT_INFO;
import static com.frans.bcmanager.pdf.DocumentConstants.FONT_OUTRO;
import static com.frans.bcmanager.pdf.DocumentConstants.FONT_TITLE;
import static com.frans.bcmanager.pdf.DocumentConstants.LEGAL_INFORMATIONS;
import static com.frans.bcmanager.pdf.DocumentConstants.MIN_HEIGHT;
import static com.frans.bcmanager.pdf.DocumentConstants.PROJECT;
import static com.lowagie.text.Element.ALIGN_JUSTIFIED;
import static com.lowagie.text.Element.ALIGN_LEFT;
import static com.lowagie.text.Element.ALIGN_MIDDLE;
import static com.lowagie.text.Element.ALIGN_RIGHT;
import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;
import static java.time.format.DateTimeFormatter.ofPattern;

public class PdfDocumentView extends AbstractPdfView {

    private com.frans.bcmanager.model.Document document;
    private String documentType;
    private Enterprise enterprise;
    private DecimalFormat twoDecimals;
    private DecimalFormat threeDecimals;

    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document, PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        this.document = (com.frans.bcmanager.model.Document) model.get("document");
        this.enterprise = (com.frans.bcmanager.model.Enterprise) model.get("enterprise");
        this.documentType = (String) model.get("documentType");
        document.addTitle(this.documentType + " " + this.document.getCode());
        response.addHeader("Content-Disposition", "inline; filename=" + this.documentType + "_" + this.document.getCode() + ".pdf");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        twoDecimals = new DecimalFormat("###,##0.00", symbols);
        threeDecimals = new DecimalFormat("###,##0.000", symbols);

        document.add(createHeader());
        document.add(createTableInfos());
        document.add(createTitle());
        document.add(createTableHeader());
        document.add(createDataTable());
        document.add(createTotalTable());
        document.add(createGreetings());
        if (documentType.equals("FACTURE")) {
            document.add(createLegalInformations());
        }
    }

    private PdfPTable createHeader() throws DocumentException {
        PdfPTable tableTitle = new PdfPTable(2);
        tableTitle.setWidths(new float[]{2, 1});
        tableTitle.setWidthPercentage(100);
        Image image1;
        try {
            image1 = Image.getInstance("src/main/resources/static/images/logo.jpg");
            image1.scalePercent(100f);
            PdfPCell cellLeft = new PdfPCell(image1);
            cellLeft.setBorder(0);
            cellLeft.setHorizontalAlignment(ALIGN_LEFT);
            tableTitle.addCell(cellLeft);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Phrase phraseDate = new Phrase("Namur, le " + document.getCreationDate().format(ofPattern("dd/MM/yyyy")));
        phraseDate.setFont(FONT_DATE);

        PdfPCell cellRight = new PdfPCell(phraseDate);
        cellRight.setBorder(0);
        cellRight.setHorizontalAlignment(ALIGN_LEFT);
        tableTitle.addCell(cellRight);
        return tableTitle;
    }

    private PdfPTable createTableInfos() throws DocumentException {
        PdfPTable tableInfo = new PdfPTable(2);
        tableInfo.setSpacingBefore(25F);
        tableInfo.setSpacingAfter(40F);
        tableInfo.setWidthPercentage(100);
        tableInfo.setHorizontalAlignment(0);
        tableInfo.setWidths(new int[]{2, 1});
        String street = document.getProject() != null ? document.getProject().getAddress().getStreet() : "";
        String postCode = document.getProject() != null ? document.getProject().getAddress().getPostCode() : "";
        String city = document.getProject() != null ? document.getProject().getAddress().getCity() : "";

        createInfosCell(tableInfo, enterprise.getName(), true);
        createInfosCell(tableInfo, document.getClient().getLastName() + " " + document.getClient().getFirstName(), true);
        createInfosCell(tableInfo, enterprise.getAddress().getStreet(), true);
        createInfosCell(tableInfo, document.getClient().getAddress().getStreet(), true);
        createInfosCell(tableInfo, enterprise.getAddress().getCity(), true);
        createInfosCell(tableInfo, document.getClient().getAddress().getPostCode() + " " + document.getClient().getAddress().getCity(),
                        true);
        createInfosCell(tableInfo, enterprise.getPhoneNumber(), true);
        createInfosCell(tableInfo, "Tel. : " + document.getClient().getPhoneNumber(), true);
        createInfosCell(tableInfo, enterprise.getMail(), true);
        createInfosCell(tableInfo, "Email : " + document.getClient().getMail(), !document.getClient().getMail().isEmpty());
        createInfosCell(tableInfo, enterprise.getBicNumber(), true);
        createInfosCell(tableInfo, "N° TVA : " + document.getClient().getTaxNumber(), !document.getClient().getTaxNumber().isEmpty());
        createInfosCell(tableInfo, enterprise.getIbanNumber(), true);
        createInfosCell(tableInfo, "", true);
        createInfosCell(tableInfo, enterprise.getTaxNumber(), true);
        createInfosCell(tableInfo, PROJECT, document.getProject() != null);
        createInfosCell(tableInfo, enterprise.getRegisterDate().format(ofPattern("dd/MM/yyyy")), true);
        createInfosCell(tableInfo, street, document.getProject() != null);
        createInfosCell(tableInfo, "", true);
        createInfosCell(tableInfo, postCode + " " + city, document.getProject() != null);

        return tableInfo;
    }

    private void createInfosCell(PdfPTable infoTable,
                                 String value,
                                 boolean display) {
        PdfPCell cell = new PdfPCell(new Phrase(display ? value : "", FONT_INFO));
        cell.setBorder(0);
        cell.setHorizontalAlignment(ALIGN_LEFT);
        infoTable.addCell(cell);
    }

    private Paragraph createTitle() {
        Chunk title = new Chunk(documentType + " " + document.getCode());
        title.setFont(FONT_DOCUMENT_CODE);
        Paragraph paraTitle = new Paragraph();
        paraTitle.add(title);
        paraTitle.setAlignment(ALIGN_LEFT);
        paraTitle.setSpacingAfter(40);
        return paraTitle;
    }

    private PdfPTable createTableHeader() throws DocumentException {
        PdfPTable tableHeader = new PdfPTable(6);
        tableHeader.setSpacingAfter(0);
        tableHeader.setWidthPercentage(100);
        tableHeader.setHorizontalAlignment(0);
        tableHeader.setWidths(new float[]{1f, 2.5f, 1f, 1f, 1.5f, 1f});

        createHeaderCell(tableHeader, "POSTE N°", 0, ALIGN_LEFT);
        createHeaderCell(tableHeader, "TRAVAUX", 0, ALIGN_LEFT);
        createHeaderCell(tableHeader, "UNITE", 0, ALIGN_RIGHT);
        createHeaderCell(tableHeader, "PRIX U.", 0, ALIGN_RIGHT);
        createHeaderCell(tableHeader, "QUANTITE", 0, ALIGN_RIGHT);
        createHeaderCell(tableHeader, "MONTANT", 0, ALIGN_RIGHT);
        createHeaderCell(tableHeader, "", 1, ALIGN_RIGHT);
        createHeaderCell(tableHeader, "", 1, ALIGN_RIGHT);
        createHeaderCell(tableHeader, "", 1, ALIGN_RIGHT);
        createHeaderCell(tableHeader, "", 1, ALIGN_RIGHT);
        createHeaderCell(tableHeader, "", 1, ALIGN_RIGHT);
        createHeaderCell(tableHeader, "", 1, ALIGN_RIGHT);

        return tableHeader;
    }

    private void createHeaderCell(PdfPTable tableHeader, String value, int border, int hAlign) {
        PdfPCell cell = new PdfPCell(new Phrase(value, FONT_TITLE));
        cell.setBorder(border);
        cell.setBorderColorBottom(WHITE);
        cell.setBorderColorLeft(WHITE);
        cell.setBorderColorRight(WHITE);
        cell.setBorderColorTop(BLACK);
        cell.setHorizontalAlignment(hAlign);
        cell.setVerticalAlignment(ALIGN_MIDDLE);
        tableHeader.addCell(cell);
    }

    private PdfPTable createDataTable() throws DocumentException {
        PdfPTable dataTable = new PdfPTable(6);
        dataTable.setSpacingAfter(40F);
        dataTable.setWidthPercentage(100);
        dataTable.setHorizontalAlignment(0);
        dataTable.setWidths(new float[]{1f, 2.5f, 1f, 1f, 1.5f, 1f});

        int index = 1;

        for (DocumentLine documentLine : document.getDocumentLines()) {
            createDataTableLine(dataTable, index, documentLine);
            index++;
        }

        return dataTable;
    }

    private void createDataTableLine(PdfPTable dataTable, int index, DocumentLine documentLine) {

        createDataTableCell(dataTable, String.valueOf(index), ALIGN_LEFT);
        createDataTableCell(dataTable, documentLine.getDescription(), ALIGN_LEFT);
        createDataTableCell(dataTable, documentLine.getUnit().getDisplayName(), ALIGN_RIGHT);
        createDataTableCell(dataTable, twoDecimals.format(documentLine.getPrice()), ALIGN_RIGHT);
        createDataTableCell(dataTable, threeDecimals.format(documentLine.getQuantity()), ALIGN_RIGHT);
        createDataTableCell(dataTable, twoDecimals.format(documentLine.getTotal()), ALIGN_RIGHT);
    }

    private void createDataTableCell(PdfPTable dataTable, String value, int hAlign) {
        PdfPCell dataCell = new PdfPCell(new Phrase(value, FONT_INFO));
        dataCell.setBorder(0);
        dataCell.setHorizontalAlignment(hAlign);
        dataCell.setVerticalAlignment(ALIGN_MIDDLE);
        dataCell.setMinimumHeight(MIN_HEIGHT);
        dataTable.addCell(dataCell);
    }

    private PdfPTable createTotalTable() throws DocumentException {
        PdfPTable totalTable = new PdfPTable(2);
        totalTable.setSpacingAfter(40F);
        totalTable.setWidthPercentage(30);
        totalTable.setHorizontalAlignment(ALIGN_RIGHT);
        totalTable.setWidths(new float[]{1f, 1f});
        totalTable.setKeepTogether(true);

        createTotalCell(totalTable, "Total HTVA", ALIGN_LEFT);
        createTotalCell(totalTable, twoDecimals.format(document.getSubTotal()) + " EUR", ALIGN_RIGHT);
        createTotalCell(totalTable, "TVA (" + (document.getTaxRate().getValue().multiply(
                new BigDecimal("100"))).intValueExact() + "%)", ALIGN_LEFT);
        createTotalCell(totalTable, twoDecimals.format(document.getTotalTax()) + " EUR", ALIGN_RIGHT);
        createTotalCell(totalTable, "Total TVAC", ALIGN_LEFT);
        createTotalCell(totalTable, twoDecimals.format(document.getTotal()) + " EUR", ALIGN_RIGHT);

        return totalTable;
    }

    private void createTotalCell(PdfPTable totalTable, String value, int hAlign) {
        PdfPCell cell = new PdfPCell(new Phrase(value, FONT_TITLE));
        cell.setBorder(0);
        cell.setHorizontalAlignment(hAlign);
        cell.setVerticalAlignment(ALIGN_MIDDLE);
        cell.setMinimumHeight(MIN_HEIGHT);
        totalTable.addCell(cell);
    }

    private Paragraph createLegalInformations() {
        Chunk phraseOutro = new Chunk(LEGAL_INFORMATIONS);
        phraseOutro.setFont(FONT_OUTRO);
        Paragraph paraOutro = new Paragraph();
        paraOutro.add(phraseOutro);
        paraOutro.setAlignment(ALIGN_JUSTIFIED);
        paraOutro.setKeepTogether(true);
        return paraOutro;
    }

    private Paragraph createGreetings() {
        String greet = document.getPaymentDate() != null ? "Date Limite de paiement : " + document.getPaymentDate().format(
                ofPattern("dd/MM/yyyy")) + ".\n\n" : "";

        String com = document.getStructuredCommunication();
        greet += com != null ? "Communication structurée : +++" + com.substring(0, 3) + "/" + com.substring(3,7) + "/" + com.substring(7,12) + "+++\n\n" : "";


        greet += "Nous restons à votre disposition pour toute information complémentaire.\nCordialement,\n\nBoris Bertrand\n\n";
        Chunk phraseGreet = new Chunk(greet);
        phraseGreet.setFont(FONT_GREET);
        Paragraph paraGreet = new Paragraph();
        paraGreet.add(phraseGreet);

        paraGreet.setAlignment(ALIGN_JUSTIFIED);
        paraGreet.setSpacingAfter(50F);
        paraGreet.setKeepTogether(true);
        return paraGreet;
    }
}
