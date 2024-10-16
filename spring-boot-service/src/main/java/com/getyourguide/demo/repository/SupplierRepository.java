package com.getyourguide.demo.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getyourguide.demo.model.Activity;
import com.getyourguide.demo.model.Supplier;
import com.getyourguide.demo.service.JSONResourceReaderService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class SupplierRepository {

    private List<Supplier> suppliers;
    private final JSONResourceReaderService jsonResourceReader;

    @Autowired
    public SupplierRepository(JSONResourceReaderService jsonResourceReader) {
        this.jsonResourceReader = jsonResourceReader;
    }

    @PostConstruct
    public void loadSuppliers() throws IOException {
        // TODO: Can be removed to add a data store like Redis or even elastic search while moving to production
        String fileInputStream = jsonResourceReader.readJsonFile("static/suppliers.json");
        ObjectMapper objectMapper = new ObjectMapper();
        suppliers = objectMapper.readValue(fileInputStream, new TypeReference<>() {
        });
    }

    public Optional<Supplier> getSupplierByID(Long id) {
        return suppliers.stream()
                .filter(supplier -> Objects.equals(supplier.getId(), id))
                .findFirst();
    }
}
