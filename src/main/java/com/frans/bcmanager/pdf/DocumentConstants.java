package com.frans.bcmanager.pdf;

import com.lowagie.text.Font;

import static com.lowagie.text.Font.BOLD;
import static com.lowagie.text.Font.ITALIC;
import static com.lowagie.text.Font.NORMAL;

public class DocumentConstants {

    public static final Font FONT_TITLE = new Font(Font.UNDEFINED, 9, BOLD);
    public static final Font FONT_INFO = new Font(Font.UNDEFINED, 9, NORMAL);
    public static final Font FONT_DATE = new Font(Font.UNDEFINED, 13, NORMAL);
    public static final Font FONT_GREET = new Font(Font.UNDEFINED, 10, NORMAL);
    public static final Font FONT_OUTRO = new Font(Font.UNDEFINED, 10, ITALIC);
    public static final Font FONT_DOCUMENT_CODE = new Font(Font.UNDEFINED, 17, BOLD);
    public static final int MIN_HEIGHT = 20;
    public static final String NAME = "Bertrand Boris";
    public static final String STREET = "Rue du progrès 1";
    public static final String CITY = "5300 Petit-Warêt";
    public static final String PHONE_NUMBER = "Tel. : 0472/06.73.39";
    public static final String EMAIL = "Email : contact@bertrand-construction.be";
    public static final String BIC = "BIC : BBRUBEBB";
    public static final String IBAN = "IBAN : BE13 3770 5768 6139";
    public static final String TVA = "N° TVA : 0600834826";
    public static final String PROJECT = "Chantier :";
    public static final String REGISTER_DATE = "Date d'enregistrement : 01/04/2015";

    public static final String LEGAL_INFORMATIONS = "Remarques importantes : \n\n" +
                                                    "- Extrait de nos conditions générales : Nos factures sont payables dans les quinze " +
                                                    "jours de leur émission. Les " +
                                                    "montants" +
                                                    " impayés échus produisent un intérêt au taux de 1,5% par mois de retard. Les " +
                                                    "portions de mois sont comptées pour " +
                                                    "un " +
                                                    "mois entier, par la seule échéance du terme sans mise en demeure préalable, et " +
                                                    "jusqu'à réception du paiement " +
                                                    "complet. " +
                                                    "De plus, les frais de recouvrement  \"prise en charge du dossier par l'avocat, " +
                                                    "huissier, société spécialisée, etc" +
                                                    ".\" " +
                                                    "sont à charge du débiteur. En cas de défaut de paiement, l'entrepreneur peut " +
                                                    "s'adresser aux tribunaux de Namur, " +
                                                    "seuls " +
                                                    "compétents. Les frais seront à charge du débiteur.\n\n";
}
