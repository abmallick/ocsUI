package com.ocs.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

import com.ocs.bean.AppointmentBean;
import com.ocs.bean.DoctorBean;
import com.ocs.bean.PatientBean;
import javax.swing.table.DefaultTableModel;

public interface Administrator {

	String addDoctor(DoctorBean doctorBean);
	String modifyDcotor(DoctorBean doctorBean);
	ArrayList<DoctorBean> viewAllDoctors();
	int removeDoctor(String doctorID);
	ArrayList<DoctorBean> suggestDoctors();
	Map<PatientBean, AppointmentBean> viewPatientsByDate(Date appointmetnDate);
	
	
}
