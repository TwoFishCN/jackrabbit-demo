package com.example.jackrabbit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;

@RestController
@RequestMapping(value = "/show")
public class ShowController {

    private final Session session;

    @Autowired
    public ShowController(Session session) {
        this.session = session;
    }

    public ResponseEntity workspace() throws RepositoryException {
        Workspace workspace = session.getWorkspace();
        return ResponseEntity.ok(workspace);
    }
}
