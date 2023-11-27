package com.asdc.dalbites.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.asdc.dalbites.util.UtilityFunctions;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {FirebaseFileServiceImpl.class})
@ExtendWith(SpringExtension.class)
class FirebaseFileServiceImplTest {
    @Autowired
    private FirebaseFileServiceImpl firebaseFileServiceImpl;

    @MockBean
    private ResourceLoader resourceLoader;

    @MockBean
    private UtilityFunctions utilityFunctions;

    /**
     * Method under test: {@link FirebaseFileServiceImpl#uploadFile(MultipartFile)}
     */
    @Test
    void testUploadFile() throws IOException {
        MockMultipartFile file = mock(MockMultipartFile.class);
        when(file.getBytes()).thenThrow(new IOException("foo"));
        when(file.getContentType()).thenReturn("text/plain");
        when(file.getOriginalFilename()).thenReturn("foo.txt");
        assertThrows(IOException.class, () -> firebaseFileServiceImpl.uploadFile(file));
        verify(file).getBytes();
        verify(file).getContentType();
        verify(file).getOriginalFilename();
    }
}
