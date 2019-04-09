package com.ren.controller;

import com.alibaba.fastjson.JSON;
import com.ren.service.SysGeneratorService;
import com.ren.utils.DateUtils;
import com.ren.utils.PageUtils;
import com.ren.utils.Query;
import com.ren.utils.R;
import com.ren.xss.XssHttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author ren
 */
@Controller
@RequestMapping("")
public class SysGeneratorController {

    @Autowired
    private SysGeneratorService sysGeneratorService;

    @RequestMapping("select")
    public  String  select(){
        return "../views/pages/sys/generator";
    }
    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping(value = "views/pages/sys/generator")
    public R list(@RequestParam Map<String,Object> params) {
        //查询列表数据
        System.err.println(params);
        Query query = new Query(params);
        List<Map<String, Object>> list = sysGeneratorService.queryList(query);
        int total = sysGeneratorService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * 生成代码
     */
    @RequestMapping("/sys/generator/code")
//    @RequiresPermissions("sys:generator:code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.err.println(">>>>>>>>>>>>>生成开始");
        String[] tableNames = new String[]{};
        //获取表名，不进行xss过滤
        HttpServletRequest orgRequest = XssHttpServletRequestWrapper.getOrgRequest(request);
        String tables = orgRequest.getParameter("tables");
        tableNames = JSON.parseArray(tables).toArray(tableNames);

        byte[] data = sysGeneratorService.generatorCode(tableNames);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"AutoCode"
                + DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_YYYY_MM_DD_HH_MM_SS_SSS) + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
