package com.pruu.pombo.model.repository;

import com.pruu.pombo.model.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, String>, JpaSpecificationExecutor<Report> {
}
