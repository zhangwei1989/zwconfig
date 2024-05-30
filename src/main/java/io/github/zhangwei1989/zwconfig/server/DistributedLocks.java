package io.github.zhangwei1989.zwconfig.server;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * DistributedLocks
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/30
 */
@Slf4j
@Component
public class DistributedLocks {

    @Autowired
    DataSource dataSource;

    Connection connection;

    @Getter
    AtomicBoolean locked = new AtomicBoolean(false);

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void init() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        executor.scheduleWithFixedDelay(this::tryLock, 1000, 5000, TimeUnit.MILLISECONDS);
    }

    public void tryLock() {
        try {
            lock();
            locked.set(true);
        } catch (Exception ex) {
            log.info("======> [ZwConfig] get lock failed......");
            locked.set(false);
        }
    }

    @SneakyThrows
    public boolean lock() {
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        connection.createStatement().execute("set innodb_lock_wait_timeout = 5");
        connection.createStatement().execute("select * from locks where id = 1 for update");

        if (locked.get()) {
            log.info("======> [ZwConfig] reenter this dist lock");
        } else {
            log.info("======> [ZwConfig] get lock succeed");
        }

        return true;
    }

    @PreDestroy
    public void close() {
        log.info("======> [ZwConfig] close datasource connection......");
        try {
            if (!connection.isClosed()) {
                connection.rollback();
                connection.close();
                locked.set(false);
            }
        } catch (SQLException e) {
            log.info("======> [ZwConfig] encounter SQLException when close datasource connection");
        }
    }

}
