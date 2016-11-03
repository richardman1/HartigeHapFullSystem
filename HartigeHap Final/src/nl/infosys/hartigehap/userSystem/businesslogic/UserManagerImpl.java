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

    public UserManagerImpl() {
        userDao = new UserDao();
    }

    @Override
    public boolean login(String username, String password) {
        return userDao.login(username, password);
    }

    @Override
    public void logout() {
        userDao.logout(username);
    }

    @Override
    public String getFunctie() {
        return userDao.getFunctie();
    }
}
