package com.example.jackrabbit.config;

import com.google.common.collect.Lists;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.security.user.UserPerWorkspaceUserManager;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.manager.impl.ObjectContentManagerImpl;
import org.apache.jackrabbit.ocm.mapper.Mapper;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.AnnotationMapperImpl;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;

@Configuration
public class RabbitInitConfig {

    private final Session adminSession;

    @Autowired
    public RabbitInitConfig(Session session) {
        this.adminSession = session;
    }

    @Bean
    public ObjectContentManager objectContentManager() {

        /*TODO Maybe I should load the base package name from the configuration file.*/
        Reflections reflections = new Reflections("com.example.jackrabbit.domain");

        ArrayList<Class> classArrayList = Lists.newArrayList(reflections.getTypesAnnotatedWith(Node.class));

        Mapper mapper = new AnnotationMapperImpl(classArrayList);
        return new ObjectContentManagerImpl(adminSession, mapper);
    }

    @Bean
    public UserManager userManager() throws RepositoryException {
        SessionImpl session = (SessionImpl) adminSession;
        return new UserPerWorkspaceUserManager(session, session.getUserID());
    }
}
