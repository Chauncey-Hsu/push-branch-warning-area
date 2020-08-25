package com.tongbo.ctbt.pushbranchwarningarea.web;

import com.tongbo.ctbt.pushbranchwarningarea.service.AreaAlarmRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lenovo
 */
@RestController
@RequestMapping("hello")
public class HelloController {

    @Autowired
    private AreaAlarmRecordService areaAlarmRecordService;

    @RequestMapping("jpa/{id}")
    public Object jpaTest(@PathVariable String id){
        return areaAlarmRecordService.getAreaAlarmRecordById(Long.valueOf(id));
    }


    @RequestMapping("world/{year}")
    public Object helloWorld(@PathVariable String year){
        System.out.println("hello world !" + year);
        return "success";
    }
}
