package com.app.trans.controllers;

import com.app.trans.dtos.CompanyDataDTO;
import com.app.trans.dtos.CompanyInfoDTO;
import com.app.trans.dtos.CoreValueDTO;
import com.app.trans.dtos.TeamDepartmentDTO;
import com.app.trans.exceptions.CompanyNotFoundException;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.exceptions.UnauthorizedException;
import com.app.trans.models.Company;
import com.app.trans.models.CompanyInfo;
import com.app.trans.models.CompanyService;
import com.app.trans.security.JwtService;
import com.app.trans.services.CompanyDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyDataController {

    private final JwtService jwtService;
    private final CompanyDataService companyDataService;

    @GetMapping("/data")
    public ResponseEntity<CompanyDataDTO> getCompanyData(
            @RequestHeader("Authorization") String header
    ) {
        String token = jwtService.getTokenFromHeader(header);
        UUID companyId = jwtService.extractCompanyId(token);
        try {
            CompanyDataDTO data = companyDataService.getCompanyDataByUUID(companyId);
            return ResponseEntity.ok(data);
        } catch (CompanyNotFoundException e) {
            log.error("Company not found: {}", companyId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error fetching company data for company: {}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<Company> getCompany(
            @RequestHeader("Authorization") String header
    ) {
        String token = jwtService.getTokenFromHeader(header);
        UUID companyId = jwtService.extractCompanyId(token);
        try {
            Company company = companyDataService.getCompanyById(companyId);
            CompanyInfo info = company.getInfo();
            info.setCompany(Company.builder().build());
            company.setInfo(info);
            return ResponseEntity.ok(company);
        } catch (CompanyNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/info")
    public ResponseEntity<CompanyInfoDTO> updateCompanyInfo(
            @RequestHeader("Authorization") String header,
            @RequestBody CompanyInfoDTO dto
    ) {
        String token = jwtService.getTokenFromHeader(header);
        UUID companyId = jwtService.extractCompanyId(token);
        try {
            CompanyInfoDTO updated = companyDataService.updateCompanyInfo(companyId, dto);
            return ResponseEntity.ok(updated);
        } catch (CompanyNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error updating company info for company: {}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/values")
    public ResponseEntity<CoreValueDTO> addCoreValue(
            @RequestBody CoreValueDTO dto,
            @RequestHeader("Authorization") String header
    ) {
        String token = jwtService.getTokenFromHeader(header);
        UUID companyId = jwtService.extractCompanyId(token);
        try {
            CoreValueDTO created = companyDataService.addCoreValue(companyId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (CompanyNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error creating core value for company: {}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/values/{valueId}")
    public ResponseEntity<Void> deleteCoreValue(
            @RequestHeader("Authorization") String header,
            @PathVariable UUID valueId
    ) {
        String token = jwtService.getTokenFromHeader(header);
        UUID companyId = jwtService.extractCompanyId(token);
        try {
            companyDataService.deleteCoreValue(companyId, valueId);
            return ResponseEntity.noContent().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error deleting core value for company: {}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/services")
    public ResponseEntity<CompanyService> addService(
            @RequestHeader("Authorization") String header,
            @RequestBody String title
    ) {
        String token = jwtService.getTokenFromHeader(header);
        UUID companyId = jwtService.extractCompanyId(token);
        try {
            CompanyService created = companyDataService.addService(companyId, title);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (CompanyNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error creating service for company: {}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/services/{serviceId}")
    public ResponseEntity<Void> deleteService(
            @RequestHeader("Authorization") String header,
            @PathVariable UUID serviceId
    ) {
        String token = jwtService.getTokenFromHeader(header);
        UUID companyId = jwtService.extractCompanyId(token);
        try {
            companyDataService.deleteService(companyId, serviceId);
            return ResponseEntity.noContent().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error deleting service for company: {}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/team")
    public ResponseEntity<TeamDepartmentDTO> addTeamDepartment(
            @RequestHeader("Authorization") String header,
            @RequestBody TeamDepartmentDTO dto
    ) {
        String token = jwtService.getTokenFromHeader(header);
        UUID companyId = jwtService.extractCompanyId(token);
        try {
            TeamDepartmentDTO created = companyDataService.addTeamDepartment(companyId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (CompanyNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error creating team department for company: {}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/team/{deptId}")
    public ResponseEntity<Void> deleteTeamDepartment(
            @RequestHeader("Authorization") String header,
            @PathVariable UUID deptId
    ) {
        String token = jwtService.getTokenFromHeader(header);
        UUID companyId = jwtService.extractCompanyId(token);
        try {
            companyDataService.deleteTeamDepartment(companyId, deptId);
            return ResponseEntity.noContent().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error deleting team department for company: {}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}