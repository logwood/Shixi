package org.example.shixi.service.base;

import cn.hutool.core.lang.Assert;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.shixi.tables.entity.UserEntity;
import org.example.shixi.util.JsonUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ExcelUserListener extends AnalysisEventListener<UserEntity> {

    List<UserEntity> list = new ArrayList<>();
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     */
    public ExcelUserListener(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data 获取到的数据
     * @param context 基本背景，用于调整状态
     */
    @Override
    public void invoke(UserEntity data, AnalysisContext context) {
        try {
            System.out.println("解析到一条数据:{"+ JsonUtil.toJsonStr(data)+"}");
            data.setPassword(passwordEncoder.encode(data.getPassword()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        list.add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context 基本被禁
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doAfterAllAnalysed(AnalysisContext context) {
        try {
            Assert.notNull(list);
            userService.saveBatch(list);
            System.out.println(JsonUtil.toJsonStr(list));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
