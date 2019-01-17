package com.en.adback.service;

import com.en.adback.entity.dic.Enterprise;

import java.util.List;

public interface EnterpriseService {

    public List<Enterprise> selectEnterprise();

    public Enterprise saveEnterprise(Enterprise enterprise);

    public Enterprise updateEnterprise(Enterprise enterprise);

    public Enterprise deleteEnterprise(String enterpriseId);
}
