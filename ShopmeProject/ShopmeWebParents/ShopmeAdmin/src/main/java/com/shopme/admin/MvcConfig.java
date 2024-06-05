package com.shopme.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // open directory from file system for client can access file
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirName = "user-photos";
        Path userPhotoDir = Paths.get(dirName);

        System.out.println("---------------------");
        System.out.println(userPhotoDir);
        System.out.println("---------------------");

        String userPhotoPath = userPhotoDir.toFile().getAbsolutePath();
        System.out.println("---------------------");
        System.out.println(userPhotoPath);
        System.out.println("---------------------");

        
        registry.addResourceHandler("/" + dirName + "/**") // allow all file in directory
                .addResourceLocations("file:/" + userPhotoPath + "/"); // config path route to resource 'file:/'
    }

}
