/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocs.dao;

import com.ocs.bean.*;
import com.ocs.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author root
 */
public class OcsDAO {
    
    public boolean changePassword(String id, String password, String newP)
    {
        int row = 0;
        String sql = "SELECT USERID,LOGINSTATUS FROM OCS_TBL_User_Credentials WHERE USERID = ? AND PASSWORD = ?";
        String sql2 = "UPDATE OCS_TBL_User_Credentials SET PASSWORD = ? WHERE USERID = ?";
        try(Connection conn = DBConnection.getDBConnection())
        {
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setString(1, id);
         ps.setString(2, password);
         ResultSet rs = ps.executeQuery();
         if(rs.next())
         {
             PreparedStatement ps2 = conn.prepareStatement(sql2);
             ps2.setString(1, newP);
             ps2.setString(2, id);
             
             row = ps2.executeUpdate();
             
         }
         else
         {
             System.out.println("Invalid current password");
         }
        } catch (SQLException ex) {
            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return row > 0;
        }
    }
    public DefaultTableModel getDoctorModel()
    {
        String sql = "select * from OCS_TBL_Doctor";
        Vector<String> colNames = new Vector<>();
        colNames.add("DOCTORID");
        colNames.add("NAME");
        colNames.add("DOB");
        colNames.add("DOJ");
        colNames.add("Gender");
        colNames.add("Qualification");
        colNames.add("Specialization");
        colNames.add("Years of Experience");
        colNames.add("Street");
        colNames.add("Location");
        colNames.add("City");
        colNames.add("State");
        colNames.add("Pincode");
        colNames.add("Contact No.");
        colNames.add("Email ID");
        
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        
        try(Connection conn = DBConnection.getDBConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            Vector<String> row = null;
            
            while(rs != null && rs.next())
            {
                row = new Vector<>();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(String.valueOf(rs.getDate(3)));
                row.add(String.valueOf(rs.getDate(4)));
                row.add(rs.getString(5));
                row.add(rs.getString(6));
                row.add(rs.getString(7)); 
                row.add(String.valueOf(rs.getInt(8)));
                row.add(rs.getString(9)); 
                row.add(rs.getString(10)); 
                row.add(rs.getString(11)); 
                row.add(rs.getString(12)); 
                row.add(rs.getString(13)); 
                row.add(rs.getString(14)); 
                row.add(rs.getString(15)); 
                data.add(row);
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return new DefaultTableModel(data,colNames);
    }
    public String create(CredentialsBean user)
    {
        String sql = "INSERT INTO OCS_TBL_User_Credentials VALUES (?,?,?,?)";
        int rows = 0;
        try(Connection conn = DBConnection.getDBConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserID());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getUserType());
            ps.setInt(4, user.getLoginStatus());
            
            rows = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(rows > 0)
                return "Success";
            else
                return "Failure";
        }
    }
    public String create(DoctorBean doctorBean)
    {
        String sql = "INSERT INTO OCS_TBL_Doctor VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int rows = 0;
        try(Connection conn = DBConnection.getDBConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, doctorBean.getDoctorID());
                ps.setString(2, doctorBean.getDoctorName());
                ps.setDate(3, doctorBean.getDateOfBirth());
                ps.setDate(4, doctorBean.getDateOfJoining());
                ps.setString(5, doctorBean.getGender());
                ps.setString(6, doctorBean.getQualification());
                ps.setString(7, doctorBean.getSpecialization());
                ps.setInt(8, doctorBean.getYearsOfExperience());
                ps.setString(9, doctorBean.getStreet());
                ps.setString(10, doctorBean.getLocation());
                ps.setString(11, doctorBean.getCity());
                ps.setString(12, doctorBean.getState());
                ps.setString(13, doctorBean.getPincode());
                ps.setString(14, doctorBean.getContactNumber());
                ps.setString(15, doctorBean.getEmailID());
                
                rows = ps.executeUpdate();
                        
        } catch (SQLException ex) {
            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally
                {
                   if(rows>0) 
                       return "Success";
                   else
                       return "Failure";
                }
    }
    
    public String create(ProfileBean profileBean)
    {
        String sql = "INSERT INTO OCS_TBL_User_Profile VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        int rows = 0;
        try(Connection conn = DBConnection.getDBConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, profileBean.getUserID());
                ps.setString(2, profileBean.getFirstName());
                ps.setString(3, profileBean.getLastName());
                ps.setDate(4, profileBean.getDateOfBirth());
                ps.setString(5, profileBean.getGender());
                ps.setString(6, profileBean.getStreet());
                ps.setString(7, profileBean.getLocation());
                ps.setString(8, profileBean.getCity());
                ps.setString(9, profileBean.getState());
                ps.setString(10, profileBean.getPincode());
                ps.setString(11, profileBean.getMobileNo());
                ps.setString(12, profileBean.getEmailID());
                
                rows = ps.executeUpdate();
                        
        } catch (SQLException ex) {
            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally
                {
                   if(rows>0) 
                       return "Success";
                   else
                       return "Failure";
                }
    }
    
    public String deleteDoctor(String doctorID)
    {
        String sql = "delete from OCS_TBL_Doctor where DOCTORID=?";
		int row = 0;
		try(Connection conn = DBConnection.getDBConnection())
		{
		
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, doctorID);
		
		row = ps.executeUpdate();
		
		} catch (SQLException ex) {
			Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally{
                    if(row > 0)
                        return "Success";
                    else
                        return "Failure";
                }
    }
    
    public String update(DoctorBean doctorBean)
    {
        String sql = "delete from OCS_TBL_Doctor where DOCTORID=?";
        String sql2 = "INSERT INTO OCS_TBL_Doctor VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int row = 0;
		try(Connection conn = DBConnection.getDBConnection())
		{
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, doctorBean.getDoctorID());
		row = ps.executeUpdate();
                
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                
                ps.setString(1, doctorBean.getDoctorID());
                ps.setString(2, doctorBean.getDoctorName());
                ps.setDate(3, doctorBean.getDateOfBirth());
                ps.setDate(4, doctorBean.getDateOfJoining());
                ps.setString(5, doctorBean.getGender());
                ps.setString(6, doctorBean.getQualification());
                ps.setString(7, doctorBean.getSpecialization());
                ps.setInt(8, doctorBean.getYearsOfExperience());
                ps.setString(9, doctorBean.getStreet());
                ps.setString(10, doctorBean.getLocation());
                ps.setString(11, doctorBean.getCity());
                ps.setString(12, doctorBean.getState());
                ps.setString(13, doctorBean.getPincode());
                ps.setString(14, doctorBean.getContactNumber());
                ps.setString(15, doctorBean.getEmailID());
                
                row = ps.executeUpdate();
		
		} catch (SQLException ex) {
			Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally{
                    if(row > 0)
                        return "Success";
                    else
                        return "Failure";
                }
    }
    
    public String update(ProfileBean profileBean)
    {
        String sql = "delete from OCS_TBL_User_Profile where USERID=?";
        String sql2 = "INSERT INTO OCS_TBL_User_Profile VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		int row = 0;
		try(Connection conn = DBConnection.getDBConnection())
		{
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, profileBean.getUserID());
		row = ps.executeUpdate();
		
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                
                ps.setString(1, profileBean.getUserID());
                ps.setString(2, profileBean.getFirstName());
                ps.setString(3, profileBean.getLastName());
                ps.setDate(4, profileBean.getDateOfBirth());
                ps.setString(5, profileBean.getGender());
                ps.setString(6, profileBean.getStreet());
                ps.setString(7, profileBean.getLocation());
                ps.setString(8, profileBean.getCity());
                ps.setString(9, profileBean.getState());
                ps.setString(10, profileBean.getPincode());
                ps.setString(11, profileBean.getMobileNo());
                ps.setString(12, profileBean.getEmailID());
                
                row = ps.executeUpdate();
                
		} catch (SQLException ex) {
			Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally{
                    if(row > 0)
                        return "Success";
                    else
                        return "Failure";
                }
    }
    
    public DoctorBean findByID(DoctorBean doctorBean)
    {
        String sql = "SELECT * FROM OCS_TBL_Doctor WHERE DOCTORID=?";
        DoctorBean d = new DoctorBean();
        try(Connection conn = DBConnection.getDBConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, doctorBean.getDoctorID());
            ResultSet rs = ps.executeQuery();
            d.setDoctorID(rs.getString(1));
            d.setDoctorName(rs.getString(2));
            d.setDateOfBirth(rs.getDate(3));
            d.setDateOfJoining(rs.getDate(4));
            d.setGender(rs.getString(5));
            d.setQualification(rs.getString(6));
            d.setSpecialization(rs.getString(7));
            d.setYearsOfExperience(rs.getInt(8));
            d.setStreet(rs.getString(9));
            d.setLocation(rs.getString(10));
            d.setCity(rs.getString(11));
            d.setState(rs.getString(12));
            d.setPincode(rs.getString(13));
            d.setContactNumber(rs.getString(14));
            d.setEmailID(rs.getString(15));
            
        } catch (SQLException ex) {
            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return d;
        }
    }
    
    public PatientBean findByID(PatientBean patientBean)
    {
        String sql = "SELECT * FROM OCS_TBL_Patient WHERE PATIENTID=?";
        PatientBean p = new PatientBean();
        try(Connection conn = DBConnection.getDBConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, patientBean.getPatientID());
            ResultSet rs = ps.executeQuery();
            
            p.setPatientID(rs.getString(1));
            p.setAppointmentDate(rs.getDate(2));
            p.setAilmentType(rs.getString(3));
            p.setAilmentDetails(rs.getString(4));
            p.setDiagnosisHistory(rs.getString(5));
            
        } catch (SQLException ex) {
            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return p;
        }
    }
    
    public ArrayList<DoctorBean> findAllDoctor()
    {
        String sql = "SELECT * FROM OCS_TBL_Doctor";
		ArrayList<DoctorBean> list = new ArrayList<>();
		try(Connection conn = DBConnection.getDBConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				DoctorBean d = new DoctorBean();
                                
                                d.setDoctorID(rs.getString(1));
                                d.setDoctorName(rs.getString(2));
                                d.setDateOfBirth(rs.getDate(3));
                                d.setDateOfJoining(rs.getDate(4));
                                d.setGender(rs.getString(5));
                                d.setQualification(rs.getString(6));
                                d.setSpecialization(rs.getString(7));
                                d.setYearsOfExperience(rs.getInt(8));
                                d.setStreet(rs.getString(9));
                                d.setLocation(rs.getString(10));
                                d.setCity(rs.getString(11));
                                d.setState(rs.getString(12));
                                d.setPincode(rs.getString(13));
                                d.setContactNumber(rs.getString(14));
                                d.setEmailID(rs.getString(15));
                                
				list.add(d);
			}
		} catch (SQLException ex) {
                                Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return list;
    }
    
    public ArrayList<ProfileBean> findAllPatient()
    {
        String sql = "SELECT * FROM OCS_TBL_User_Profile";
		ArrayList<ProfileBean> list = new ArrayList<>();
		try(Connection conn = DBConnection.getDBConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				ProfileBean pb = new ProfileBean();
                                
                                pb.setUserID(rs.getString(1));
                                pb.setFirstName(rs.getString(2));
                                pb.setLastName(rs.getString(3));
                                pb.setDateOfBirth(rs.getDate(4));
                                pb.setGender(rs.getString(5));
                                pb.setStreet(rs.getString(6));
                                pb.setLocation(rs.getString(7));
                                pb.setCity(rs.getString(8));
                                pb.setState(rs.getString(9));
                                pb.setPincode(rs.getString(10));
                                pb.setMobileNo(rs.getString(11));
                                pb.setEmailID(rs.getString(12));
                                
				list.add(pb);
			}
		} catch (SQLException ex) {
                                Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return list;
    }
    
}
