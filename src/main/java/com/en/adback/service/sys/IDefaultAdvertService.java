package com.en.adback.service.sys;

import com.en.adback.entity.sys.DefaultAdvert;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2018/12/8.
 */
public interface IDefaultAdvertService {
    //查询全部
    List<DefaultAdvert> getDefaultAdvertList();
    // 保存
    int insertDefaultAdvert(DefaultAdvert defaultAdvert);
}
