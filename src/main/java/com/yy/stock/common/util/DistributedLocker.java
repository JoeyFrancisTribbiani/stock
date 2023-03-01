package com.yy.stock.common.util;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * Redisson分布式锁接口
 * <p>
 * RLock的实现有可重入非公平锁（RedissonLock）、可重入公平锁（RedissonFairLock）、联锁（RedissonMultiLock）、 红锁（RedissonRedLock）、 读锁（RedissonReadLock）、 写锁（RedissonWriteLock）等
 */
public interface DistributedLocker {
    RLock lock(String lockKey);

    RLock lock(String lockKey, long timeout);

    RLock lock(String lockKey, TimeUnit unit, long timeout);

    boolean tryLock(String lockKey, TimeUnit unit, long leaseTime);

    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);
}
