package com.whhxz.tracking.profiler;

import com.whhxz.bootstrap.tracking.Agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * ClassFileTransformerDispatcher
 * Created by xuzhuo on 2018/3/9.
 */
public class ClassFileTransformerDispatcher implements ClassFileTransformer {
    public ClassFileTransformerDispatcher(Agent agent) {
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return new byte[0];
    }
}
