package com.frans.bcmanager.service;

import com.frans.bcmanager.model.Enterprise;
import com.frans.bcmanager.repository.EnterpriseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    public EnterpriseService(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }

    public Optional<Enterprise> getEnterprise() {
        return enterpriseRepository.findFirstBy();
    }

    public void save(Enterprise enterprise) {
        enterpriseRepository.save(enterprise);
    }
}
