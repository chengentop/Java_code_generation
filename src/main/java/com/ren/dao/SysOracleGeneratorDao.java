package com.ren.dao;



import com.ren.entity.ResultMap;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * oracle代码生成器
 *
 * @author ren
 */
@Repository
public interface SysOracleGeneratorDao {

    List<Map<String, Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<ResultMap> queryColumns(String tableName);
}
