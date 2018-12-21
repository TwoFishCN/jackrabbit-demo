package com.example.jackrabbit.service;

import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jcr.RepositoryException;
import java.util.List;

@Service
public class UserService {

    private final UserManager userManager;
    private final ObjectContentManager objectContentManager;

    @Autowired
    public UserService(UserManager userManager, ObjectContentManager objectContentManager) {
        this.userManager = userManager;
        this.objectContentManager = objectContentManager;
    }

    public User create(String name, String password) throws RepositoryException {
        return userManager.createUser(name, password);
    }

    public List<User> list() {
        /*TODO 实现具体内容*/
        return null;
    }
}
