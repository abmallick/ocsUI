/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocs.util;

import com.ocs.bean.CredentialsBean;
import com.ocs.dao.OcsDAO;
import java.awt.CardLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class UserAuthentication {


    public boolean authenticate(CredentialsBean user) {
        
        boolean con = false;
        String sql = "SELECT USERID FROM OCS_TBL_User_Credentials WHERE USERID = ? AND PASSWORD = ?";
        try(Connection conn = DBConnection.getDBConnection())
        {
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserID());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                changeLoginStatus(user, 1);
                con = true;
            }
            else{ 
                con =  false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return con;
        }
    }


    public String authorize(String userID) {
        String aut = "Loggedin";
        String sql = "SELECT LOGINSTATUS FROM OCS_TBL_User_Credentials WHERE USERID = ?";
        try(Connection conn = DBConnection.getDBConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if(rs.getInt(1) == 0)
            {
                aut = "Not Loggedin";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return aut;
        }
    }


    public boolean changeLoginStatus(CredentialsBean user, int loginStatus) {
        boolean val = false;
        String sql = "UPDATE OCS_TBL_User_Credentials SET LOGINSTATUS = ? WHERE USERID = ?";
        try(Connection conn = DBConnection.getDBConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, loginStatus);
            ps.setString(2, user.getUserID());
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                val = true;
            }
            else
            {
                val = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return val;
        }
    }
    
}
