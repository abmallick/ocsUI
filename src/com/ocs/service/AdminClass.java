/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocs.service;

import com.ocs.bean.AppointmentBean;
import com.ocs.bean.DoctorBean;
import com.ocs.bean.LeaveBean;
import com.ocs.bean.PatientBean;
import com.ocs.bean.ProfileBean;
import com.ocs.dao.OcsDAO;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author root
 */
public class AdminClass implements Administrator {

    public LeaveBean report(String doctorID)
    {
        OcsDAO od = new OcsDAO();
        return od.docReport(doctorID);
    }
    public boolean modifyPassword(String id, String password, String newP)
    {
        OcsDAO od = new OcsDAO();
        return od.changePassword(id, password, newP);
    }
    
    @Override
    public String addDoctor(DoctorBean doctorBean) {
        String res;
        OcsDAO od = new OcsDAO();
        res = od.create(doctorBean);
        return res;
    }

    @Override
    public String modifyDcotor(DoctorBean doctorBean) {
        String res;
        OcsDAO od = new OcsDAO();
        res = od.update(doctorBean);
        return res;
    }

    public DefaultTableModel viewDoctors()
    {
        OcsDAO od = new OcsDAO();
        return od.getDoctorModel();
    }
    
    @Override
    public ArrayList<DoctorBean> viewAllDoctors() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public int removeDoctor(String doctorID) {
        String res;
        OcsDAO od = new OcsDAO();
        res = od.deleteDoctor(doctorID);
        if(res.equalsIgnoreCase("Success"))
            return 1;
        else
            return 0;
        
    }

    @Override
    public ArrayList<DoctorBean> suggestDoctors() {
        OcsDAO od = new OcsDAO();
        return od.findAllDoctor();
    }

    public ArrayList<ProfileBean> suggestPatient() {
        OcsDAO od = new OcsDAO();
        return od.findAllPatient();
    }
    
    public PatientBean findPatient(String pid) {
        OcsDAO od = new OcsDAO();
        return od.findByID(pid);
    }
    
    public boolean availID(String userID)
    {
        OcsDAO od = new OcsDAO();
        return od.availableID(userID);
    }
    
    @Override
    public Map<PatientBean, AppointmentBean> viewPatientsByDate(Date appointmetnDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
