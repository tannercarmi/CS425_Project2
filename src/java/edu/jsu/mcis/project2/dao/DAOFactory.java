/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.project2.dao;

import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author tanto
 */

public class DAOFactory {
    
    private DataSource ds = null;

    public DAOFactory() {

        try {
            Context envContext = new InitialContext();
            Context initContext = (Context) envContext.lookup("java:/comp/env");
            ds = (DataSource) initContext.lookup("jdbc/db_pool");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    Connection getConnection() {

        Connection c = null;

        try {

            if (ds != null) {
                c = ds.getConnection();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return c;

    }
    
    public SearchDAO getSearchDAO() {
        return new SearchDAO(this);
    }
    
    public RegistrationDAO getRegistrationDAO() {
        return new RegistrationDAO(this);
    }
}
