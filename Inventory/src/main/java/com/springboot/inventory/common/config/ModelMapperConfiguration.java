package com.springboot.inventory.common.config;

import org.modelmapper.ModelMapper;
<<<<<<< HEAD
import org.modelmapper.convention.MatchingStrategies;
=======
>>>>>>> origin/seunghyeok
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
<<<<<<< HEAD
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.LOOSE);

=======
>>>>>>> origin/seunghyeok
        return new ModelMapper();
    }

}
