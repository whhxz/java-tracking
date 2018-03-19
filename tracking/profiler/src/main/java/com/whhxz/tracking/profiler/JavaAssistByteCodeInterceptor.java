package com.whhxz.tracking.profiler;

import com.whhxz.bootstrap.tracking.Agent;
import com.whhxz.bootstrap.tracking.ByteCodeInterceptor;

/**
 * JavaAssistByteCodeInterceptor
 * Created by xuzhuo on 2018/3/9.
 */
public class JavaAssistByteCodeInterceptor implements ByteCodeInterceptor {
    private Agent agent;

    public JavaAssistByteCodeInterceptor(Agent agent) {
        this.agent = agent;
    }
}
