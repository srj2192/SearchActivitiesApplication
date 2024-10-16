package com.getyourguide.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JSONResourceReaderServiceTest {

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private Resource resource;

    @InjectMocks
    private JSONResourceReaderService jsonResourceReaderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReadJsonFile_Success() throws IOException {
        String filePath = "test.json";
        String expectedContent = "{\"key\": \"value\"}";

        when(resourceLoader.getResource("classpath:" + filePath)).thenReturn(resource);
        when(resource.exists()).thenReturn(true);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(expectedContent.getBytes()));

        String result = jsonResourceReaderService.readJsonFile(filePath);

        assertEquals(expectedContent, result);
        verify(resourceLoader).getResource("classpath:" + filePath);
        verify(resource).exists();
        verify(resource).getInputStream();
    }

    @Test
    void testReadJsonFile_FileNotFound() {
        String filePath = "nonexistent.json";

        when(resourceLoader.getResource("classpath:" + filePath)).thenReturn(resource);
        when(resource.exists()).thenReturn(false);

        assertThrows(IOException.class, () -> jsonResourceReaderService.readJsonFile(filePath));

        verify(resourceLoader).getResource("classpath:" + filePath);
        verify(resource).exists();
    }

    @Test
    void testReadJsonFile_IOExceptionOnRead() throws IOException {
        String filePath = "error.json";

        when(resourceLoader.getResource("classpath:" + filePath)).thenReturn(resource);
        when(resource.exists()).thenReturn(true);
        when(resource.getInputStream()).thenThrow(new IOException("Read error"));

        assertThrows(IOException.class, () -> jsonResourceReaderService.readJsonFile(filePath));

        verify(resourceLoader).getResource("classpath:" + filePath);
        verify(resource).exists();
        verify(resource).getInputStream();
    }
}
