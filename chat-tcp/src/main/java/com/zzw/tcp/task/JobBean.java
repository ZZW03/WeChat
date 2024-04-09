package com.zzw.tcp.task;



import com.zzw.tcp.service.LimServer;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.QuartzJobBean;




@Lazy
public class JobBean extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {


        new LimServer().start();
//        new LimWebSocketServer().start();

    }

}
