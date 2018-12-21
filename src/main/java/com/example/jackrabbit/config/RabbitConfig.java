package com.example.jackrabbit.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.ConfigurationException;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.jackrabbit.core.config.RepositoryConfigurationParser;
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
import java.util.Properties;

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

        File file = ResourceUtils.getFile(configFilename);

        try (InputStream inputStream = new FileInputStream(file)) {
            return RepositoryConfig.create(new InputSource(inputStream), properties);
        }
    }

    private Repository jcrRepository() throws Exception {
        RepositoryConfig config = create();
        return RepositoryImpl.create(config);
    }

    @Bean
    public Session adminSession() throws Exception {
        return jcrRepository().login(new SimpleCredentials("admin", "superSecret!".toCharArray()));
    }

}
