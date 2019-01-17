package com.en.adback.service.sys;

import com.en.adback.entity.sys.SysSetting;

public interface SysSettingService {

    public SysSetting getSettingParams();

    public int settingParams(SysSetting sysSetting);
}
