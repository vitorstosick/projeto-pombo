package com.pruu.pombo.controller;

import com.pruu.pombo.exception.PruuException;
import com.pruu.pombo.model.entity.Report;
import com.pruu.pombo.model.selector.ReportSelector;
import com.pruu.pombo.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService service;

    @PostMapping
    public ResponseEntity<Report> save(@Valid @RequestBody Report report) {
        return ResponseEntity.ok(service.save(report));
    }

    @PostMapping("/filters")
    public List<Report> filters(@RequestBody ReportSelector selector) {
        return service.filters(selector);
    }

    @GetMapping
    public List<Report> findAll(@RequestParam String userId) throws PruuException {
        return service.findAll(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> findById(@PathVariable String id, @RequestParam String userId) throws PruuException {
        Report report = service.findById(id, userId);
        return ResponseEntity.ok(report); // Retorna 200 OK com o report encontrado
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws PruuException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
