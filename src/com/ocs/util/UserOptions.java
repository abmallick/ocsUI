/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocs.util;

import com.ocs.bean.CredentialsBean;

/**
 *
 * @author root
 */
public class UserOptions {
    
    public boolean login(CredentialsBean user)
    {
        UserAuthentication ua = new UserAuthentication();
        if(ua.authenticate(user))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean logout(CredentialsBean user)
    {
        UserAuthentication ua = new UserAuthentication();
        if(ua.changeLoginStatus(user, 0))
        {
            return true;
        }
        else {
            return false;
        }
    }
}
