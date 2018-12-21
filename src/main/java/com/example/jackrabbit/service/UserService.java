package com.example.jackrabbit.service;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jcr.RepositoryException;
import java.util.Iterator;

@Service
public class UserService {

    private final UserManager userManager;

    @Autowired
    public UserService(UserManager userManager) {
        this.userManager = userManager;
    }

    public User create(String name, String password) throws RepositoryException {
        return userManager.createUser(name, password);
    }

    public Iterator<Authorizable> list(String path) throws RepositoryException {
        return userManager.findAuthorizables(path, null);
    }
}
