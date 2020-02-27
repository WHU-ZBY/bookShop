package com.zby.bookshop.service;

import com.zby.bookshop.dao.SysLogMapper;
import com.zby.bookshop.pojo.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    public int insert(SysLog sysLog)
    {
        return sysLogMapper.insert(sysLog);
    }

}
