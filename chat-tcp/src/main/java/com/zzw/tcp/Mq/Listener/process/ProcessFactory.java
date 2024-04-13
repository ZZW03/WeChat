package com.zzw.tcp.Mq.Listener.process;


public class ProcessFactory {
    //默认使用BaseProcess
    private static BaseProcess defaultProcess;

    static {
        defaultProcess = new BaseProcess() {
            @Override
            public void processBefore() {

            }

            @Override
            public void processAfter() {

            }
        };
    }

    //也可以选择其他的process处理
    public static BaseProcess getMessageProcess(Integer command) {
        return defaultProcess;
    }

}
