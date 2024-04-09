package com.zzw.tcp.configuation;


import com.zzw.tcp.task.JobBean;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class QuartzConfiguration {

    @Bean
    public JobDetail jobDetailFactoryBean(){
        return JobBuilder.newJob(JobBean.class)
                //名字
                .withIdentity("Tcp-WebSocket")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger cronTriggerFactoryBean(JobDetail jobDetail){

//        CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule("* * * * * ?");

        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(1) // 设置间隔为1毫秒
                .withRepeatCount(0); // 设置重复次数为0，表示只执行一次
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("Tcp-WebSocket")
                .withSchedule(simpleScheduleBuilder)
                .build();
    }
}
