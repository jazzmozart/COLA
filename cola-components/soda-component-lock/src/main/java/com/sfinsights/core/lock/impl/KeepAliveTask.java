package com.sfinsights.core.lock.impl;

import io.etcd.jetcd.Lease;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: distributed-lock
 * @description: 基于etcd lease机制的续约任务
 * @author: 行百里者
 * @create: 2020/10/14 17:44
 **/
@Slf4j
public class KeepAliveTask implements Runnable {

    private Lease leaseClient;
    private long leaseId;

    public KeepAliveTask(Lease leaseClient, long leaseId) {
        this.leaseClient = leaseClient;
        this.leaseId = leaseId;
    }

    @Override
    public void run() {
        log.info("租约续约，租约ID：{}", leaseId);
        this.leaseClient.keepAliveOnce(leaseId);
    }
}
