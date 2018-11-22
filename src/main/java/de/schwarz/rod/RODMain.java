package de.schwarz.rod;

import org.apache.commons.cli.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RODMain {
    
	
	/**
	 * Main method
	 * @param args cli arguments
	 */
	public static void main(String[] args) {

        Options options = new Options();

        Option dbNameOpt = new Option("db", "dbname", true, "database name where the email addresses are saved");
        dbNameOpt.setRequired(true);
        options.addOption(dbNameOpt);

        Option tableOpt = new Option("t", "table", true, "database table name where the email addresses are saved");
        tableOpt.setRequired(true);
        options.addOption(tableOpt);

        Option whereOpt = new Option("w", "where", true, "MySQL WHERE-Clause that should match to be deleted. " +
                "Example: nl_freigabe <> 'yes' AND nl_date <= (NOW() - INTERVAL 3 MONTH)");
        whereOpt.setRequired(true);
        options.addOption(whereOpt);

        Option portOpt = new Option("p", "port", true, "database port");
        portOpt.setRequired(false);
        options.addOption(portOpt);

        CommandLineParser cliParser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try{
            cmd = cliParser.parse(options, args);
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("java -jar build/libs/remove_old_data-<VERSION>.jar", options);

            System.exit(1);
        }

        String databaseName = cmd.getOptionValue("dbname");
        String tableName = cmd.getOptionValue("table");
        String where = cmd.getOptionValue("where");
        String port = cmd.getOptionValue("port");

        if(port == null) {
            port = "3306";
        }
        deleteEmailAddressesFromDB(databaseName, tableName, where, port);
	}




	private static void deleteEmailAddressesFromDB(String databaseName,
                                                   String tableName,
                                                   String where,
                                                   String port) {

        Connection connection = DBConnection.getConnection(databaseName, port);
        if(connection == null) {
            return;
        }

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("DELETE FROM " + tableName +" WHERE " + where);
            int updateCount = stmt.executeUpdate();
        }


        catch (NullPointerException e) {
            System.out.println("NullPointerException");
        }
        catch (SQLException e) {
            System.out.println("SQL query not executed: " + e.toString());
        }
	}

	
}
