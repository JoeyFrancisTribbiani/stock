package com.yy.stock.common.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redisson分布式锁实现-使用可重入非公平锁（RedissonLock）
 */
@Component
public class RedissonDistributedLocker implements DistributedLocker {
    @Autowired
    private RedissonClient redissonClient;

    public RLock lock(String lockKey) {
        RLock lock = this.redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    public RLock lock(String lockKey, long timeout) {
        RLock lock = this.redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;
    }

    public RLock lock(String lockKey, TimeUnit unit, long timeout) {
        RLock lock = this.redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    public boolean tryLock(String lockKey, TimeUnit unit, long leaseTime) {
        RLock lock = this.redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(0L, leaseTime, unit);
        } catch (InterruptedException var7) {
            return false;
        }
    }

    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (lock.isLocked()) {
                lock.unlock();
            }
        } catch (IllegalMonitorStateException localIllegalMonitorStateException) {
        }
    }

    public void unlock(RLock lock) {
        try {
            if (lock.isLocked()) {
                lock.unlock();
            }
        } catch (IllegalMonitorStateException var3) {
        }
    }
}
