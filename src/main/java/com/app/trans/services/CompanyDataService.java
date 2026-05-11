package com.app.trans.services;

import com.app.trans.dtos.CompanyDataDTO;
import com.app.trans.dtos.CompanyInfoDTO;
import com.app.trans.dtos.CoreValueDTO;
import com.app.trans.dtos.TeamDepartmentDTO;
import com.app.trans.exceptions.CompanyNotFoundException;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.exceptions.UnauthorizedException;
import com.app.trans.models.*;
import com.app.trans.repos.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyDataService {

    private final CompanyRepo companyRepository;
    private final CompanyInfoRepo companyInfoRepository;
    private final CoreValueRepo coreValueRepository;
    private final CompanyServiceRepo serviceRepository;
    private final TeamDepartmentRepo teamDepartmentRepository;

    public CompanyDataDTO getCompanyDataByUUID(UUID companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found: " + companyId));

        Optional<CompanyInfo> info = companyInfoRepository.findByCompanyId(companyId);
        List<CoreValue> values = coreValueRepository.findByCompanyIdOrderByOrderAsc(companyId);
        List<CompanyService> services = serviceRepository.findByCompanyIdOrderByOrderAsc(companyId);
        List<TeamDepartment> teams = teamDepartmentRepository.findByCompanyIdOrderByOrderAsc(companyId);

        return CompanyDataDTO.builder()
                .name(company.getName())
                .description(company.getDescription())
                .companyInfo(info.map(CompanyInfo::toDTO).orElse(null))
                .values(values.stream().map(CoreValue::toDTO).toList())
                .services(services.stream().map(CompanyService::toDTO).toList())
                .teamSize(teams.stream().map(TeamDepartment::toDTO).toList())
                .build();
    }

    public Company getCompanyById(UUID companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found: " + companyId));
    }

    public Company createCompany(String name, String description) {
        Company company = new Company();
        company.setName(name);
        company.setDescription(description);
        company.setCreatedAt(Instant.now());
        company.setUpdatedAt(Instant.now());
        return companyRepository.save(company);
    }

    public CompanyInfoDTO updateCompanyInfo(UUID companyId, CompanyInfoDTO dto) {
        CompanyInfo info = companyInfoRepository.findByCompanyId(companyId)
                .orElseGet(CompanyInfo::new);

        if (info.getCompany() == null) {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow();
            info.setCompany(company);
        }

        info.setFoundedYear(dto.getFoundedYear());
        info.setEmployees(dto.getEmployees());
        info.setHeadquarters(dto.getHeadquarters());
        info.setOurMission(dto.getMission());
        info.setOurVision(dto.getVision());
        info.setUpdatedAt(Instant.now());

        CompanyInfo saved = companyInfoRepository.save(info);
        return saved.toDTO();
    }

    public CoreValueDTO addCoreValue(UUID companyId, CoreValueDTO dto) {
        Company company = getCompanyById(companyId);

        CoreValue value = CoreValue.builder()
                .icon(dto.getIcon())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .company(company)
                .createdAt(Instant.now())
                .build();

        CoreValue saved = coreValueRepository.save(value);
        return saved.toDTO();
    }

    public void deleteCoreValue(UUID companyId, UUID valueId) {
        CoreValue value = coreValueRepository.findById(valueId)
                .orElseThrow(() -> new ResourceNotFoundException("Core value not found: " + valueId));

        if (!value.getCompany().getId().equals(companyId)) {
            throw new UnauthorizedException("Core value does not belong to this company");
        }

        coreValueRepository.deleteById(valueId);
    }

    public CompanyService addService(UUID companyId, String title) {
        Company company = getCompanyById(companyId);

        CompanyService service = new CompanyService();
        service.setCompany(company);
        service.setTitle(title);
        service.setCreatedAt(Instant.now());

        return serviceRepository.save(service);
    }

    public void deleteService(UUID companyId, UUID serviceId) {
        CompanyService service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found: " + serviceId));

        if (!service.getCompany().getId().equals(companyId)) {
            throw new UnauthorizedException("Service does not belong to this company");
        }

        serviceRepository.deleteById(serviceId);
    }

    public TeamDepartmentDTO addTeamDepartment(UUID companyId, TeamDepartmentDTO dto) {
        Company company = getCompanyById(companyId);

        TeamDepartment dept = TeamDepartment.builder()
                .role(dto.getRole())
                .count(dto.getCount())
                .description(dto.getDescription())
                .company(company)
                .createdAt(Instant.now())
                .build();

        TeamDepartment saved = teamDepartmentRepository.save(dept);
        return saved.toDTO();
    }

    public void deleteTeamDepartment(UUID companyId, UUID deptId) {
        TeamDepartment dept = teamDepartmentRepository.findById(deptId)
                .orElseThrow(() -> new ResourceNotFoundException("Team department not found: " + deptId));

        if (!dept.getCompany().getId().equals(companyId)) {
            throw new UnauthorizedException("Team department does not belong to this company");
        }

        teamDepartmentRepository.deleteById(deptId);
    }
}