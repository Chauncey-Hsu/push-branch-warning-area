package com.tongbo.ctbt.pushbranchwarningarea;

import com.tongbo.ctbt.pushbranchwarningarea.util.EmailUtil;
import com.tongbo.ctbt.pushbranchwarningarea.web.InitDataWeb;
import com.tongbo.ctbt.pushbranchwarningarea.web.ReceiveLocMqWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 随容器的启动而启动此类的run方法
 *
 * @author lenovo
 */
@Component
public class LocConsumerRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(LocConsumerRunner.class);

    @Autowired
    ReceiveLocMqWeb receiveLocMqWeb;
    @Autowired
    InitDataWeb initDataWeb;

    @Override
    public void run(ApplicationArguments args) {
        // 接收报位和处理报位的开始
        try {
            receiveLocMqWeb.receive();
        } catch (Exception e) {
            e.printStackTrace();
            EmailUtil.sendEmail("chuanqijob@163.com", "【push-branch-warning-area】接收并处理报位数据for警戒区域", e.getMessage());
        }

        // 初始化一些参数
        initDataWeb.process();
    }
}
