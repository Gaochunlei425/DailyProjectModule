package com.company.project.controller;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.UserTest;
import com.company.project.service.UserTestService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2020/12/08.
*/
@RestController
@RequestMapping(value = "/user/test")
public class UserTestController {
    @Resource
    private UserTestService userTestService;

    @ApiOperation(value = "新增")
    @ApiImplicitParams({@ApiImplicitParam(name = "userTest", value = "", required = true, dataType = "String")})
    @PostMapping(value = "/add", produces = "application/json", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Result add(@RequestParam(value = "userTest") UserTest userTest) {
        userTestService.save(userTest);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "", required = true, dataType = "int")})
    @DeleteMapping(value = "/delete", produces = "application/json")
    public Result delete(@RequestParam(value = "id") int id) {
        userTestService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({@ApiImplicitParam(name = "userTest", value = "", required = true, dataType = "String")})
    @PutMapping(value = "/update", produces = "application/json", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Result update(@RequestParam(value = "userTest") UserTest userTest) {
        userTestService.update(userTest);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "查询详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "", required = true, dataType = "int")})
    @GetMapping(value = "/detail",produces = "application/json")
    public Result detail(@RequestParam(value = "id") int id) {
        UserTest userTest = userTestService.findById(id);
        return ResultGenerator.genSuccessResult(userTest);
    }

    @ApiOperation(value = "查询列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页码", required = false, dataType = "int"),
                        @ApiImplicitParam(name = "size", value = "页面大小", required = false, dataType = "int")})
    @GetMapping(value = "/list",produces = "application/json")
    public Result list( @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                        @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        PageHelper.startPage(page, size);
        List<UserTest> list = userTestService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
