package com.ren;

import com.ren.dao.SysGeneratorDao;
import com.ren.service.SysGeneratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonGeneratorApplicationTests {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SysGeneratorDao sysGeneratorDao;
    @Test
    public void contextLoads() throws SQLException {
        System.err.println(dataSource.getConnection());
    }
    @Test
    public void select() throws SQLException {
        System.err.println(sysGeneratorDao.queryTable("t_table"));
    }
}

