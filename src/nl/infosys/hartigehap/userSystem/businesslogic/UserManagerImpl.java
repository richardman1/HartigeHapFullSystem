/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.userSystem.businesslogic;

import nl.infosys.hartigehap.userSystem.databaseacces.UserDao;

/**
 *
 * @author Sander van Belleghem
 */
public class UserManagerImpl implements UserManager {

    private final UserDao userDao;
    private String username;

    /**
     * Creates UserMangerImpl
     */
    public UserManagerImpl() {
        userDao = new UserDao();
    }

    /**
     * Method for loggin in an user
     *
     * @param username
     * @param password
     * @return boolean true or false
     */
    @Override
    public boolean login(String username, String password) {
        return userDao.login(username, password);
    }

    /**
     * Method for loggin out an user
     */
    @Override
    public void logout() {
        userDao.logout(username);
    }

    /**
     * Method for getting his function
     *
     * @return function of an employee
     */
    @Override
    public String getFunctie() {
        return userDao.getFunctie();
    }
}
