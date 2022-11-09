package com.unionbankng.future.authorizationserver.constants;

import java.util.Collection;
import java.util.List;

public class KulaAdminConstants {



    public static final String ADMINISTRATOR = "Administrator";
    public static final String IT_CONTROL = "IT Control";
    public static final String PRODUCT_TEAM = "Product Team";
    public static final String CPC = "CPC";
    public static final String OPERATIONS_SETTLEMENT_AND_RECONCILIATION = "Operations (Settlement & Reconciliation)";
    public static final String HELP_DESK = "Help Desk";
    public static final Collection<String> ADMINISTRATOR_PERMISSIONS = List.of("View dashboard","Resolve KYC Request", "View transactions",
            "Download transaction reports","Download reports", "Issue resolution", "Role management", "Role assignment", " User management",
            " User assignment", "View audit trail", "Download audit trail report");

    public static final Collection<String> IT_PERMISSIONS = List.of("View dashboard" , "Role management", "Role assignment", " User management",
            " User assignment", "View audit trail", "Download audit trail report");

    public static final Collection<String> PRODUCT_TEAM_PERMISSIONS = List.of("View dashboard" , "Resolve KYC Request","View transactions","Download reports",
            "Download transaction reports", "Issue resolution", "View audit trail", "Download audit trail report");

    public static final Collection<String> CPC_PERMISSIONS = List.of("View dashboard" , "Resolve KYC Request","Download reports:KYC");

    public static final Collection<String> OPERATIONS_SETTLEMENT_AND_RECONCILIATION_PERMISSIONS = List.of("View dashboard" , "Download transaction reports", "View transactions","Download reports");

    public static final Collection<String>HELP_DESK_PERMISSIONS = List.of("View dashboard" , "Download transaction reports", "Issue resolution", "Download reports:complaints and enquiry ");
}
