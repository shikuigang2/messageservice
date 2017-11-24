package com.ztth.api.path.biz;

import com.ztth.api.path.entity.ApiPath;
import com.ztth.api.path.mapper.ApiPathMapper;
import com.ztth.core.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiPathBiz  extends BaseBiz<ApiPathMapper,ApiPath> {

    @Autowired
    private ApiPathMapper apiPathMapper ;
    @Override
    public void insertSelective(ApiPath entity) {
        super.insertSelective(entity);
    }

    @Override
    public void updateSelectiveById(ApiPath entity) {
        super.updateSelectiveById(entity);
    }
    @Override
    public List<ApiPath> selectListAll() {
        return super.selectListAll();
    }

}
