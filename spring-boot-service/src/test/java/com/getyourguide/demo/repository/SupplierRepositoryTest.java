package com.getyourguide.demo.repository;

import com.getyourguide.demo.model.Supplier;
import com.getyourguide.demo.service.JSONResourceReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SupplierRepositoryTest {

    @Mock
    private JSONResourceReaderService jsonResourceReader;

    private SupplierRepository supplierRepository;

    @BeforeEach
    void setUp() throws IOException {
        String mockJson = "[{\"id\":1,\"name\":\"Supplier 1\"},{\"id\":2,\"name\":\"Supplier 2\"},{\"id\":3,\"name\":\"Supplier 3\"}]";
        when(jsonResourceReader.readJsonFile("static/suppliers.json")).thenReturn(mockJson);
        supplierRepository = new SupplierRepository(jsonResourceReader);
        supplierRepository.loadSuppliers();
    }

    @Test
    void testGetSupplierByID_ExistingID() {
        Optional<Supplier> result = supplierRepository.getSupplierByID(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Supplier 1", result.get().getName());
    }

    @Test
    void testGetSupplierByID_NonExistingID() {
        Optional<Supplier> result = supplierRepository.getSupplierByID(4L);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetSupplierByID_NullID() {
        Optional<Supplier> result = supplierRepository.getSupplierByID(null);

        assertFalse(result.isPresent());
    }
}
