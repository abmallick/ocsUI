/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocs.service;

import com.ocs.bean.LeaveBean;
import com.ocs.dao.OcsDAO;

/**
 *
 * @author root
 */
public class Reporter {
    public boolean intimateAdmin(LeaveBean leaveBean)
    {
        OcsDAO od = new OcsDAO();
        return od.reportAdmin(leaveBean);
    }
}
