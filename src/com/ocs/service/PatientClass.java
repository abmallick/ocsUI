/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocs.service;

import com.ocs.bean.AppointmentBean;
import com.ocs.bean.DoctorBean;
import com.ocs.bean.PatientBean;
import com.ocs.bean.ProfileBean;
import com.ocs.dao.OcsDAO;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author root
 */
public class PatientClass implements Patient{
    
    public boolean modifyPassword(String id, String password, String newP)
    {
        OcsDAO od = new OcsDAO();
        return od.changePassword(id, password, newP);
    }

    @Override
    public String addAilmentDetails(PatientBean patientBean) {
        OcsDAO od = new OcsDAO();
        return od.create(patientBean);
    }

    @Override
    public boolean modifyAilmentDetails(String patientID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<PatientBean> viewAilmentDetails(String patientID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<DoctorBean> viewListOfDoctors(String type, Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String requestForAppointment(AppointmentBean appointmentBean) {
        OcsDAO od = new OcsDAO();
        if(od.setAppointment(appointmentBean))
        {
            return "Success";
        }
        else {
            return "False";
        }
    }

    @Override
    public AppointmentBean viewAppointmentDetails(String patientID) {
        OcsDAO od = new OcsDAO();
        return od.findAppointment(patientID);
    }
    
    public ProfileBean findPatient(String id)
    {
        OcsDAO od = new OcsDAO();
        return od.findPatient(id);
    }
    
    public String modifyPatient(ProfileBean profileBean,String password)
    {
        OcsDAO od = new OcsDAO();
        return od.updatePatient(profileBean,password);
    }
}
