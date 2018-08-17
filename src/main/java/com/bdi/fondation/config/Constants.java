package com.bdi.fondation.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "fr";

    // Mouvements
    public static final String CREDIT = "Credit";
    public static final String DEBIT = "Debit";
    public static final String FAIT = "Fait";
    public static final String MOINS = "-";
    public static final String FINANCEMENT = "Financement";
    public static final String REMBOURSEMENT = "Rembourement";

    // Comptes
    public static final String CAISSE = "Caisse";

    // Pret
    public static final String MIS_EN_PLACE = "Mis en place";


    private Constants() {
    }
}
