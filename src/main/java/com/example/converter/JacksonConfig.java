package com.example.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class JacksonConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        super.configureJacksonObjectMapper(objectMapper);
        objectMapper.findAndRegisterModules();
    }
}
