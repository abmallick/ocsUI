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
import java.lang.Exception;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author root
 */
public class OcsDAO {
  public boolean availableID(String userID)
  {
      String sql = "SELECT * FROM OCS_TBL_User_Credentials WHERE USERID = ?";
      boolean avail = true;
      try(Connection conn = DBConnection.getDBConnection())
      {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setString(1, userID);
          ResultSet rs = ps.executeQuery();
          if(rs.next())
          {
              avail = false;
          }
      } catch (Exception ex) {
          JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
          Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
          return avail;
      }
  }
    public ProfileBean findPatient(String id)
    {
        String sql = "SELECT * FROM OCS_TBL_User_Profile WHERE USERID = ?";
        ProfileBean p = new ProfileBean();
        try(Connection conn = DBConnection.getDBConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                p.setUserID(rs.getString(1));
                p.setFirstName(rs.getString(2));
                p.setLastName(rs.getString(3));
                p.setDateOfBirth(rs.getDate(4));
                p.setGender(rs.getString(5));
                p.setState(rs.getString(6));
                p.setLocation(rs.getString(7));
                p.setCity(rs.getString(8));
                p.setState(rs.getString(9));
                p.setPincode(rs.getString(10));
                p.setMobileNo(rs.getString(11));
                p.setEmailID(rs.getString(12));
            }
            
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return p;
        }
    }
    public LeaveBean docReport(String doctorID)
    {
        LeaveBean leaveBean = new LeaveBean();
        String sql = "SELECT * FROM OCS_TBL_Leave WHERE DOCTORID = ?";
        try(Connection conn = DBConnection.getDBConnection())
        {
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setString(1, doctorID);
         ResultSet rs = ps.executeQuery();
         if(rs.next())
         {
             leaveBean.setReporterID(rs.getString(1));
             leaveBean.setDoctorID(rs.getString(2));
             leaveBean.setLeaveFrom(rs.getDate(3));
             leaveBean.setLeaveTo(rs.getDate(4));
             leaveBean.setReason(rs.getString(5));
             leaveBean.setStatus(rs.getInt(6));
         }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);

            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return leaveBean;
        }
    }
    
    public boolean reportAdmin(LeaveBean leaveBean)
    {
        int row = 0;
        String sql = "INSERT INTO OCS_TBL_Leave VALUES(?,?,?,?,?,?)";
        try(Connection conn = DBConnection.getDBConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, leaveBean.getReporterID().substring(0, 4));
            ps.setString(2, leaveBean.getDoctorID());
            ps.setDate(3, leaveBean.getLeaveFrom());
            ps.setDate(4, leaveBean.getLeaveTo());
            ps.setString(5, leaveBean.getReason());
            ps.setInt(6, leaveBean.getStatus());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(row > 0)
                return true;
            else
                return false;
        }
    }
    public AppointmentBean findAppointment(String patientID)
    {
        AppointmentBean ab = new AppointmentBean();
        String sql = "SELECT * FROM OCS_TBL_Appointments WHERE PATIENTID = ?";
        try(Connection conn = DBConnection.getDBConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, patientID);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                ab.setAppointmentID(rs.getString(1));
                ab.setDoctorID(rs.getString(2));
                ab.setPatientID(rs.getString(3));
                ab.setAppointmentDate(rs.getDate(4));
                ab.setAppointmentTime(rs.getString(5));
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return ab;
        }
    }
    public boolean setAppointment(AppointmentBean appointmentBean)
    {
        int row = 0;
        String sql = "INSERT INTO OCS_TBL_Appointments VALUES(?,?,?,?,?)";
        try(Connection conn = DBConnection.getDBConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,appointmentBean.getAppointmentID());
            ps.setString(2,appointmentBean.getDoctorID());
            ps.setString(3,appointmentBean.getPatientID());
            ps.setDate(4,appointmentBean.getAppointmentDate());
            ps.setString(5,appointmentBean.getAppointmentTime());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(row > 0)
                return true;
            else
                return false;
        }
    }
    
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
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
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
        } catch(Exception ex){
            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
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
                        
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
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
                        
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally
                {
                   if(rows>0) 
                       return "Success";
                   else
                       return "Failure";
                }
    }
    
    public String create(PatientBean patientBean)
    {
        String sql = "INSERT INTO OCS_TBL_Patient VALUES (?,?,?,?,?,?)";
        int rows = 0;
        try(Connection conn = DBConnection.getDBConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, patientBean.getPatientID());
                ps.setString(2, patientBean.getPatientID());
                ps.setDate(3, patientBean.getAppointmentDate());
                ps.setString(4, patientBean.getAilmentType());
                ps.setString(5, patientBean.getAilmentDetails());
                ps.setString(6, patientBean.getDiagnosisHistory());
                rows = ps.executeUpdate();
                        
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
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
		
		} catch (Exception ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
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
        String sql = "UPDATE OCS_TBL_Doctor SET DOCTORID = ?, DOCTORNAME = ?,DATEOFBIRTH = ?,DATEOFJOINING = ?,GENDER = ?,QUALIFICATION = ?,SPECIALIZATION = ?,YEARSOFEXPERIENCE = ?,STREET = ?,LOCATION = ?,CITY = ?,STATE = ?,PINCODE = ?,CONTACTNO = ?,EMAILID = ? WHERE DOCTORID = ?";
		int row = 0;
		try(Connection conn = DBConnection.getDBConnection())
		{
                
                PreparedStatement ps2 = conn.prepareStatement(sql);
                
                ps2.setString(1, doctorBean.getDoctorID());
                ps2.setString(2, doctorBean.getDoctorName());
                ps2.setDate(3, doctorBean.getDateOfBirth());
                ps2.setDate(4, doctorBean.getDateOfJoining());
                ps2.setString(5, doctorBean.getGender());
                ps2.setString(6, doctorBean.getQualification());
                ps2.setString(7, doctorBean.getSpecialization());
                ps2.setInt(8, doctorBean.getYearsOfExperience());
                ps2.setString(9, doctorBean.getStreet());
                ps2.setString(10, doctorBean.getLocation());
                ps2.setString(11, doctorBean.getCity());
                ps2.setString(12, doctorBean.getState());
                ps2.setString(13, doctorBean.getPincode());
                ps2.setString(14, doctorBean.getContactNumber());
                ps2.setString(15, doctorBean.getEmailID());
                ps2.setString(16, doctorBean.getDoctorID());
                row = ps2.executeUpdate();
		
		} catch (Exception ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
			Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally{
                    if(row > 0)
                        return "Success";
                    else
                        return "Failure";
                }
    }
    
    public String updatePatient(ProfileBean profileBean, String password)
    {
            String sql = "UPDATE OCS_TBL_User_Profile SET FIRSTNAME = ?,LASTNAME = ?,DATE = ?,GENDER = ?,STREET = ?,LOCATION = ?,CITY = ?,STATE = ?,PINCODE = ?,MOBILENO = ?,EMAILID = ? WHERE USERID = ?";
            String sql2 = "SELECT USERID FROM OCS_TBL_User_Credentials WHERE USERID = ? AND PASSWORD = ?";
		int row = 0;
		try(Connection conn = DBConnection.getDBConnection())
		{
		PreparedStatement ps2 = conn.prepareStatement(sql2);
                    ps2.setString(1 , profileBean.getUserID());
                    ps2.setString(2, password);
                    ResultSet rs = ps2.executeQuery();
                    if(rs.next()){
                    
                    PreparedStatement ps = conn.prepareStatement(sql);

                    ps.setString(12, profileBean.getUserID());
                    ps.setString(1, profileBean.getFirstName());
                    ps.setString(2, profileBean.getLastName());
                    ps.setDate(3, profileBean.getDateOfBirth());
                    ps.setString(4, profileBean.getGender());
                    ps.setString(5, profileBean.getStreet());
                    ps.setString(6, profileBean.getLocation());
                    ps.setString(7, profileBean.getCity());
                    ps.setString(8, profileBean.getState());
                    ps.setString(9, profileBean.getPincode());
                    ps.setString(10, profileBean.getMobileNo());
                    ps.setString(11, profileBean.getEmailID());

                    row = ps.executeUpdate();
                }
                else{
                    JOptionPane.showMessageDialog(new JFrame(), "Invalid Password", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                    
		} catch (Exception ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
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
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return d;
        }
    }
    
    public PatientBean findByID(String pid)
    {
        String sql = "SELECT * FROM OCS_TBL_Patient WHERE PATIENTID = ?";
        PatientBean p = new PatientBean();
        try(Connection conn = DBConnection.getDBConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pid);
            ResultSet rs = ps.executeQuery();
            rs.next();
            p.setPatientID(rs.getString(1));
            p.setAppointmentDate(rs.getDate(3));
            p.setAilmentType(rs.getString(4));
            p.setAilmentDetails(rs.getString(5));
            p.setDiagnosisHistory(rs.getString(6));
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
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
		} catch (Exception ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
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
		} catch (Exception ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog",
        JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(OcsDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return list;
    }
    
}
