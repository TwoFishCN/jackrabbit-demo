package com.example.jackrabbit.config;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.ConfigurationException;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.jackrabbit.core.config.RepositoryConfigurationParser;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.manager.impl.ObjectContentManagerImpl;
import org.apache.jackrabbit.ocm.mapper.Mapper;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.AnnotationMapperImpl;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.xml.sax.InputSource;

import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

@Slf4j
@Configuration
public class RabbitConfig {

    @Value("${jcr.rep.home}")
    private String jcrHome;

    @Value("${jcr.rep.config}")
    private String configFilename;

    private RepositoryConfig create() throws IOException, ConfigurationException {
        Properties properties = new Properties();
        properties.setProperty(RepositoryConfigurationParser.REPOSITORY_HOME_VARIABLE, jcrHome);

        File file = ResourceUtils.getFile("classpath:repository.xml");

        try (InputStream inputStream = new FileInputStream(file)) {
            return RepositoryConfig.create(new InputSource(inputStream), properties);
        }
    }

    @Bean
    public Repository jcrRepository() throws Exception {
        RepositoryConfig config = create();
        return RepositoryImpl.create(config);
    }

    @Bean
    public ObjectContentManager initOCM() throws Exception {

        Session session = jcrRepository().login(new SimpleCredentials("admin", "superSecret!".toCharArray()));

        Reflections reflections = new Reflections("com.example.jackrabbit.domain");
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Node.class);
        ArrayList<Class> classArrayList = Lists.newArrayList(classSet);

        Mapper mapper = new AnnotationMapperImpl(classArrayList);
        return new ObjectContentManagerImpl(session, mapper);
    }
}
