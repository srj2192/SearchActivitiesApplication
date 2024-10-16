package com.getyourguide.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class JSONResourceReaderService {

    @Autowired
    private ResourceLoader resourceLoader;

    public String readJsonFile(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filePath);
        if (!resource.exists()) {
            throw new IOException("File not found: " + filePath);
        }
        InputStream inputStream = resource.getInputStream();
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}
