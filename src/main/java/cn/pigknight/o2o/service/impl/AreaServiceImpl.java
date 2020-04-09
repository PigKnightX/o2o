package cn.pigknight.o2o.service.impl;

import cn.pigknight.o2o.dao.AreaDao;
import cn.pigknight.o2o.entity.Area;
import cn.pigknight.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
