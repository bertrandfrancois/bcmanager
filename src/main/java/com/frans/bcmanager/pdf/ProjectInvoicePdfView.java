package com.frans.bcmanager.pdf;

import com.frans.bcmanager.model.Client;
import com.frans.bcmanager.model.DocumentLine;
import com.frans.bcmanager.model.Project;
import com.frans.bcmanager.model.ProjectInvoice;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
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
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.lowagie.text.Element.ALIGN_JUSTIFIED;
import static com.lowagie.text.Element.ALIGN_LEFT;
import static com.lowagie.text.Element.ALIGN_MIDDLE;
import static com.lowagie.text.Element.ALIGN_RIGHT;
import static com.lowagie.text.Font.BOLD;
import static com.lowagie.text.Font.ITALIC;
import static com.lowagie.text.Font.NORMAL;
import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;

public class ProjectInvoicePdfView extends AbstractPdfView {

    private DecimalFormatSymbols symbols;
    private Font fontInfo, fontTitre, fontDate, fontGreet, fontOutro, fontTitle;
    private final int MIN_HEIGHT = 20;
    private ProjectInvoice projectInvoice;
    private DecimalFormat twoDecimals;
    private DecimalFormat threeDecimals;

    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document, PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        if(model.get("document") instanceof ProjectInvoice)
        projectInvoice = (ProjectInvoice) model.get("document");
        symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        twoDecimals = new DecimalFormat("###,##0.00", symbols);
        threeDecimals = new DecimalFormat("###,##0.000", symbols);
        fontTitre = new Font(Font.UNDEFINED, 9, BOLD);
        fontInfo = new Font(Font.UNDEFINED, 9, NORMAL);
        fontDate = new Font(Font.UNDEFINED, 13, NORMAL);
        fontGreet = new Font(Font.UNDEFINED, 10, NORMAL);
        fontOutro = new Font(Font.UNDEFINED, 10, ITALIC);
        fontTitle = new Font(Font.UNDEFINED, 17, BOLD);


        document.add(createTableTitle());
        document.add(createTableInfos());
        document.add(createTitle());
        document.add(createTableEntetes());
        document.add(createTableDonnees());
        document.add(createTableTotaux());
                document.add(createGreetings());
        document.add(createOutro());


    }


    private Paragraph createOutro() {
        String outro = "Remarques importantes : \n\n";
        outro += "- Extrait de nos conditions générales : Nos invoices sont payables dans les quinze jours de leur émission. Les montants impayés échus produisent un intérêt au taux de 1,5% par mois de retard. Les portions de mois sont comptées pour un mois entier, par la seule échéance du terme sans mise en demeure préalable, et jusqu'à réception du paiement complet. De plus, les frais de recouvrement  \"prise en charge du dossier par l'avocat, huissier, société spécialisée, etc.\" sont à charge du débiteur. En cas de défaut de paiement, l'entrepreneur peut s'adresser aux tribunaux de Namur, seuls compétents. Les frais seront à charge du débiteur.\n\n";
        Chunk phraseOutro = new Chunk(outro);
        phraseOutro.setFont(fontOutro);
        Paragraph paraOutro = new Paragraph();
        paraOutro.add(phraseOutro);
        paraOutro.setAlignment(ALIGN_JUSTIFIED);
        paraOutro.setKeepTogether(true);
        return paraOutro;
    }

        private Paragraph createGreetings() {
            String greet = "Date Limite de paiement : " + projectInvoice.getPaymentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".\n\nNous restons à votre disposition pour toute information complémentaire.\nCordialement,\n\nBoris Bertrand\n\n";
            Chunk phraseGreet = new Chunk(greet);
            phraseGreet.setFont(fontGreet);
            Paragraph paraGreet = new Paragraph();
            paraGreet.add(phraseGreet);

            paraGreet.setAlignment(ALIGN_JUSTIFIED);
            paraGreet.setSpacingAfter(50F);
            paraGreet.setKeepTogether(true);
            return paraGreet;
        }

    private PdfPTable createTableInfos() throws DocumentException {
        PdfPTable tableInfo = new PdfPTable(2);
        tableInfo.setSpacingBefore(25F);
        tableInfo.setSpacingAfter(40F);
        tableInfo.setWidthPercentage(100);
        tableInfo.setHorizontalAlignment(0);
        tableInfo.setWidths(new int[]{2, 1});

        PdfPCell[] cellulesInfos = new PdfPCell[24];


        cellulesInfos[0] = new PdfPCell(new Phrase("Bertrand Boris", fontInfo));
        cellulesInfos[0].setBorder(0);
        cellulesInfos[0].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[0]);

        cellulesInfos[1] = new PdfPCell(new Phrase(client().getLastName()+" " + client().getFirstName(), fontInfo));
        cellulesInfos[1].setBorder(0);
        cellulesInfos[1].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[1]);

        cellulesInfos[2] = new PdfPCell(new Phrase("Rue du progrès 1", fontInfo));
        cellulesInfos[2].setBorder(0);
        cellulesInfos[2].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[2]);

        cellulesInfos[3] = new PdfPCell(new Phrase(client().getAddress().getStreet(), fontInfo));
        cellulesInfos[3].setBorder(0);
        cellulesInfos[3].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[3]);

        cellulesInfos[4] = new PdfPCell(new Phrase("5300 Petit-Warêt", fontInfo));
        cellulesInfos[4].setBorder(0);
        cellulesInfos[4].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[4]);

        cellulesInfos[5] = new PdfPCell(new Phrase(client().getAddress().getPostCode() + " " + client().getAddress().getCity(), fontInfo));
        cellulesInfos[5].setBorder(0);
        cellulesInfos[5].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[5]);

        cellulesInfos[6] = new PdfPCell(new Phrase("Tel. : 0472/06.73.39", fontInfo));
        cellulesInfos[6].setBorder(0);
        cellulesInfos[6].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[6]);

        cellulesInfos[7] = new PdfPCell(new Phrase("Tel. : " + client().getPhoneNumber(), fontInfo));
        cellulesInfos[7].setBorder(0);
        cellulesInfos[7].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[7]);

        cellulesInfos[8] = new PdfPCell(new Phrase("Email : contact@bertrand-construction.be", fontInfo));
        cellulesInfos[8].setBorder(0);
        cellulesInfos[8].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[8]);

//        cellulesInfos[9] = new PdfPCell(new Phrase("Email : "+client().getMail(), fontInfo));
        cellulesInfos[9] = new PdfPCell(new Phrase(""));
        cellulesInfos[9].setBorder(0);
        cellulesInfos[9].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[9]);

        cellulesInfos[10] = new PdfPCell(new Phrase("BIC : BBRUBEBB", fontInfo));
        cellulesInfos[10].setBorder(0);
        cellulesInfos[10].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[10]);

        if (!client().getTaxNumber().isEmpty()) {
            cellulesInfos[11] = new PdfPCell(new Phrase("N° TVA : " + client().getTaxNumber(), fontInfo));
        } else {
            cellulesInfos[11] = new PdfPCell(new Phrase("", fontInfo));
        }
        cellulesInfos[11].setBorder(0);
        cellulesInfos[11].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[11]);

        cellulesInfos[12] = new PdfPCell(new Phrase("IBAN : BE13 3770 5768 6139", fontInfo));
        cellulesInfos[12].setBorder(0);
        cellulesInfos[12].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[12]);

        cellulesInfos[13] = new PdfPCell(new Phrase("", fontInfo));
        cellulesInfos[13].setBorder(0);
        cellulesInfos[13].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[13]);

        cellulesInfos[14] = new PdfPCell(new Phrase("N° TVA : 0600834826", fontInfo));
        cellulesInfos[14].setBorder(0);
        cellulesInfos[14].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[14]);

        cellulesInfos[15] = new PdfPCell(new Phrase("Chantier :", fontInfo));
        cellulesInfos[15].setBorder(0);
        cellulesInfos[15].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[15]);

        cellulesInfos[16] = new PdfPCell(new Phrase("Date d'enregistrement : 01/04/2015", fontInfo));
        cellulesInfos[16].setBorder(0);
        cellulesInfos[16].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[16]);

        cellulesInfos[17] = new PdfPCell(new Phrase(project().getAddress().getStreet(), fontInfo));
        cellulesInfos[17].setBorder(0);
        cellulesInfos[17].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[17]);

        cellulesInfos[18] = new PdfPCell(new Phrase("", fontInfo));
        cellulesInfos[18].setBorder(0);
        cellulesInfos[18].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[18]);

        cellulesInfos[19] = new PdfPCell(new Phrase(project().getAddress().getPostCode() + " " + project().getAddress().getCity(), fontInfo));
        cellulesInfos[19].setBorder(0);
        cellulesInfos[19].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[19]);

        cellulesInfos[20] = new PdfPCell(new Phrase("", fontInfo));
        cellulesInfos[20].setBorder(0);
        cellulesInfos[20].setHorizontalAlignment(ALIGN_LEFT);
        tableInfo.addCell(cellulesInfos[20]);
        return tableInfo;
    }

    private Paragraph createTitle() {
        Chunk title = new Chunk("Facture N°" + projectInvoice.getCode());
        title.setFont(fontTitle);
        Paragraph paraTitle = new Paragraph();
        paraTitle.add(title);
        paraTitle.setAlignment(ALIGN_LEFT);
        paraTitle.setSpacingAfter(40);
        return paraTitle;
    }

    private PdfPTable createTableEntetes() throws DocumentException {
        PdfPTable tableEntetes = new PdfPTable(6);
        tableEntetes.setSpacingAfter(0);
        tableEntetes.setWidthPercentage(100);
        tableEntetes.setHorizontalAlignment(0);
        tableEntetes.setWidths(new float[]{1f, 2.5f, 1f, 1f, 1.5f, 1f});

        PdfPCell[] cellulesEntete = new PdfPCell[12];

        cellulesEntete[0] = new PdfPCell(new Phrase("POSTE N°", fontTitre));
        cellulesEntete[1] = new PdfPCell(new Phrase("TRAVAUX", fontTitre));
        cellulesEntete[2] = new PdfPCell(new Phrase("UNITE", fontTitre));
        cellulesEntete[3] = new PdfPCell(new Phrase("PRIX U.", fontTitre));
        cellulesEntete[4] = new PdfPCell(new Phrase("QUANTITE", fontTitre));
        cellulesEntete[5] = new PdfPCell(new Phrase("MONTANT", fontTitre));

        for (int i = 0; i < 12; i++) {

            if (i < 6) {
                if (i < 2) {
                    cellulesEntete[i].setHorizontalAlignment(ALIGN_LEFT);
                } else {
                    cellulesEntete[i].setHorizontalAlignment(ALIGN_RIGHT);
                }
                cellulesEntete[i].setBorder(0);
                cellulesEntete[i].setVerticalAlignment(ALIGN_MIDDLE);
                tableEntetes.addCell(cellulesEntete[i]);

            } else {
                cellulesEntete[i] = new PdfPCell(new Phrase(""));
                cellulesEntete[i].setBorder(1);
                cellulesEntete[i].setBorderColorBottom(WHITE);
                cellulesEntete[i].setBorderColorLeft(WHITE);
                cellulesEntete[i].setBorderColorRight(WHITE);
                cellulesEntete[i].setBorderColorTop(BLACK);
                tableEntetes.addCell(cellulesEntete[i]);
            }

        }
        return tableEntetes;
    }

    private PdfPTable createTableDonnees() throws DocumentException {
        PdfPTable tableDonnees = new PdfPTable(6);
        tableDonnees.setSpacingAfter(40F);
        tableDonnees.setWidthPercentage(100);
        tableDonnees.setHorizontalAlignment(0);
        tableDonnees.setWidths(new float[]{1f, 2.5f, 1f, 1f, 1.5f, 1f});

        int numeroPoste = 1;
        int numCell = 0;

        PdfPCell[] cellulesDonnees = new PdfPCell[nombreLignesinvoices() * 6];

        for (int j = 0; j < nombreLignesinvoices(); j++) {

            cellulesDonnees[numCell] = new PdfPCell(new Phrase(String.valueOf(numeroPoste), fontInfo));
            cellulesDonnees[numCell].setBorder(0);
            cellulesDonnees[numCell].setHorizontalAlignment(ALIGN_LEFT);
            cellulesDonnees[numCell].setVerticalAlignment(ALIGN_MIDDLE);
            cellulesDonnees[numCell].setMinimumHeight(MIN_HEIGHT);
            tableDonnees.addCell(cellulesDonnees[numCell]);
            numCell++;

            cellulesDonnees[numCell] = new PdfPCell(new Phrase(documentLine(j).getDescription(), fontInfo));
            cellulesDonnees[numCell].setBorder(0);
            cellulesDonnees[numCell].setHorizontalAlignment(ALIGN_LEFT);
            cellulesDonnees[numCell].setVerticalAlignment(ALIGN_MIDDLE);
            cellulesDonnees[numCell].setMinimumHeight(MIN_HEIGHT);
            tableDonnees.addCell(cellulesDonnees[numCell]);
            numCell++;

            cellulesDonnees[numCell] = new PdfPCell(new Phrase(documentLine(j).getUnit().getDisplayName(), fontInfo));
            cellulesDonnees[numCell].setBorder(0);
            cellulesDonnees[numCell].setHorizontalAlignment(ALIGN_RIGHT);
            cellulesDonnees[numCell].setVerticalAlignment(ALIGN_MIDDLE);
            cellulesDonnees[numCell].setMinimumHeight(MIN_HEIGHT);
            tableDonnees.addCell(cellulesDonnees[numCell]);
            numCell++;

            cellulesDonnees[numCell] = new PdfPCell(new Phrase(twoDecimals.format(documentLine(j).getPrice()), fontInfo));
            cellulesDonnees[numCell].setBorder(0);
            cellulesDonnees[numCell].setHorizontalAlignment(ALIGN_RIGHT);
            cellulesDonnees[numCell].setVerticalAlignment(ALIGN_MIDDLE);
            cellulesDonnees[numCell].setMinimumHeight(MIN_HEIGHT);
            tableDonnees.addCell(cellulesDonnees[numCell]);
            numCell++;

            cellulesDonnees[numCell] = new PdfPCell(new Phrase(threeDecimals.format(documentLine(j).getQuantity()), fontInfo));

            cellulesDonnees[numCell].setBorder(0);
            cellulesDonnees[numCell].setHorizontalAlignment(ALIGN_RIGHT);
            cellulesDonnees[numCell].setVerticalAlignment(ALIGN_MIDDLE);
            cellulesDonnees[numCell].setMinimumHeight(MIN_HEIGHT);
            tableDonnees.addCell(cellulesDonnees[numCell]);
            numCell++;

            cellulesDonnees[numCell] = new PdfPCell(new Phrase(twoDecimals.format(documentLine(j).getTotal()), fontInfo));
            cellulesDonnees[numCell].setBorder(0);
            cellulesDonnees[numCell].setHorizontalAlignment(ALIGN_RIGHT);
            cellulesDonnees[numCell].setVerticalAlignment(ALIGN_MIDDLE);
            cellulesDonnees[numCell].setMinimumHeight(MIN_HEIGHT);
            tableDonnees.addCell(cellulesDonnees[numCell]);
            numCell++;

            numeroPoste++;
        }
        return tableDonnees;
    }

    private PdfPTable createTableTotaux() throws DocumentException {
        PdfPTable tableTotaux = new PdfPTable(2);
        tableTotaux.setSpacingAfter(40F);
        tableTotaux.setWidthPercentage(30);
        tableTotaux.setHorizontalAlignment(ALIGN_RIGHT);
        tableTotaux.setWidths(new float[]{1f, 1f});
        tableTotaux.setKeepTogether(true);
        PdfPCell[] cellulesTotaux = new PdfPCell[6];

        for (int i = 0; i < 6; i++) {
            switch (i) {
                case 0:
                    cellulesTotaux[i] = new PdfPCell(new Phrase("Total HTVA", fontTitre));
                    cellulesTotaux[i].setBorder(0);
                    cellulesTotaux[i].setHorizontalAlignment(ALIGN_LEFT);
                    cellulesTotaux[i].setVerticalAlignment(ALIGN_MIDDLE);
                    cellulesTotaux[i].setMinimumHeight(MIN_HEIGHT);
                    tableTotaux.addCell(cellulesTotaux[i]);
                    break;
                case 1:
                    cellulesTotaux[i] = new PdfPCell(new Phrase(twoDecimals.format(projectInvoice.getSubTotal()) + " EUR", fontTitre));
                    cellulesTotaux[i].setBorder(0);
                    cellulesTotaux[i].setHorizontalAlignment(ALIGN_RIGHT);
                    cellulesTotaux[i].setVerticalAlignment(ALIGN_MIDDLE);
                    cellulesTotaux[i].setMinimumHeight(MIN_HEIGHT);
                    tableTotaux.addCell(cellulesTotaux[i]);
                    break;
                case 2:
                    cellulesTotaux[i] = new PdfPCell(new Phrase("TVA (" + (projectInvoice.getTaxRate().getValue().multiply(new BigDecimal("100"))).intValueExact() + "%)", fontTitre));
                    cellulesTotaux[i].setBorder(0);
                    cellulesTotaux[i].setHorizontalAlignment(ALIGN_LEFT);
                    cellulesTotaux[i].setVerticalAlignment(ALIGN_MIDDLE);
                    cellulesTotaux[i].setMinimumHeight(MIN_HEIGHT);
                    tableTotaux.addCell(cellulesTotaux[i]);
                    break;
                case 3:
                    cellulesTotaux[i] = new PdfPCell(new Phrase(twoDecimals.format(projectInvoice.getTotalTax()) + " EUR", fontTitre));
                    cellulesTotaux[i].setBorder(0);
                    cellulesTotaux[i].setHorizontalAlignment(ALIGN_RIGHT);
                    cellulesTotaux[i].setVerticalAlignment(ALIGN_MIDDLE);
                    cellulesTotaux[i].setMinimumHeight(MIN_HEIGHT);
                    tableTotaux.addCell(cellulesTotaux[i]);
                    break;
                case 4:
                    cellulesTotaux[i] = new PdfPCell(new Phrase("Total TVAC", fontTitre));
                    cellulesTotaux[i].setBorder(0);
                    cellulesTotaux[i].setHorizontalAlignment(ALIGN_LEFT);
                    cellulesTotaux[i].setVerticalAlignment(ALIGN_MIDDLE);
                    cellulesTotaux[i].setMinimumHeight(MIN_HEIGHT);
                    tableTotaux.addCell(cellulesTotaux[i]);
                    break;
                case 5:
                    cellulesTotaux[i] = new PdfPCell(new Phrase(twoDecimals.format(projectInvoice.getTotal()) + " EUR", fontTitre));
                    cellulesTotaux[i].setBorder(0);
                    cellulesTotaux[i].setHorizontalAlignment(ALIGN_RIGHT);
                    cellulesTotaux[i].setVerticalAlignment(ALIGN_MIDDLE);
                    cellulesTotaux[i].setMinimumHeight(MIN_HEIGHT);
                    tableTotaux.addCell(cellulesTotaux[i]);
                    break;
            }
        }
        return tableTotaux;
    }

    private PdfPTable createTableTitle() throws DocumentException, BadElementException {
        PdfPTable tableTitle = new PdfPTable(2);
        tableTitle.setWidths(new float[]{2, 1});
        tableTitle.setWidthPercentage(100);
        Image image1;
        try {
            image1 = Image.getInstance("https://bcgestion.herokuapp.com/images/logo.jpg");
            image1.scalePercent(100f);
            PdfPCell cellLeft = new PdfPCell(image1);
            cellLeft.setBorder(0);
            cellLeft.setHorizontalAlignment(ALIGN_LEFT);
            tableTitle.addCell(cellLeft);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LocalDate date = projectInvoice.getCreationDate();
        //        String dateString = date.toString(dateFormatter);

        Phrase phraseDate = new Phrase("Namur, le " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        phraseDate.setFont(fontDate);
        Paragraph paraDate = new Paragraph();
        paraDate.add(phraseDate);


        PdfPCell cellRight = new PdfPCell(phraseDate);
        cellRight.setBorder(0);
        cellRight.setHorizontalAlignment(ALIGN_LEFT);
        tableTitle.addCell(cellRight);
        return tableTitle;
    }

    private int nombreLignesinvoices() {
        return documentLines().size();
    }

    private List<DocumentLine> documentLines() {
        return projectInvoice.getDocumentLines();
    }

    private DocumentLine documentLine(int j) {
        return projectInvoice.getDocumentLines().get(j);
    }

    private Client client() {
        return projectInvoice.getClient();
    }

    private Project project() {
        return projectInvoice.getProject();
    }

}
