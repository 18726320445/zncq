package com.igeek.zncq.config;

import com.igeek.zncq.quartz.UpDateWeekOutGoodRank;
import com.igeek.zncq.quartz.UpdateAddress;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/7 14:00
 * @email liuyia2022@163.com
 */
@Configuration
public class QuartzConfig {
    @Bean("helloJob")
    public JobDetail helloJobDetail() {
        return JobBuilder.newJob(UpdateAddress.class)
                .withIdentity("Job1")
                .usingJobData("msg", "更新途径点")
                .storeDurably()//即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }

    @Bean
    public Trigger printTimeJobTrigger() {
        // 每天八点
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/59 * * * ? ");
        return TriggerBuilder.newTrigger()
                .forJob(helloJobDetail())
                .withIdentity("quartzTaskService1")
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail MouthOneJobDetail() {
        return JobBuilder.newJob(UpDateWeekOutGoodRank.class)
                .withIdentity("Job2")
                .usingJobData("msg", "更新每星期出库产品销量前7")
                .storeDurably()//即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }

    @Bean
    public Trigger MouthOneJobTrigger() {
        // 每周一早上6点 //0 0 6 ? * MON    0 0 10 ? * MON
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 6 ? * MON");
        return TriggerBuilder.newTrigger()
                .forJob(MouthOneJobDetail())
                .withIdentity("quartzTaskService2")
                .withSchedule(cronScheduleBuilder)
                .build();
    }

}
