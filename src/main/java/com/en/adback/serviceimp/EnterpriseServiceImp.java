package com.en.adback.serviceimp;

import com.en.adback.entity.dic.Enterprise;

import com.en.adback.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnterpriseServiceImp implements EnterpriseService {


    @Override
    public List<Enterprise> selectEnterprise() {
        return null;
    }

    @Override
    public Enterprise saveEnterprise(Enterprise enterprise) {
        return null;
    }

    @Override
    public Enterprise updateEnterprise(Enterprise enterprise) {
        return null;
    }

    @Override
    public Enterprise deleteEnterprise(String enterpriseId) {
        return null;
    }
}
