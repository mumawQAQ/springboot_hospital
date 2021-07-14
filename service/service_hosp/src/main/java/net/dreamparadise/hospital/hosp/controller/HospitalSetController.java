package net.dreamparadise.hospital.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.dreamparadise.hospital.common.result.Result;
import net.dreamparadise.hospital.common.utils.MD5;
import net.dreamparadise.hospital.hosp.service.HospitalSetService;
import net.paradise.hospital.model.hosp.HospitalSet;
import net.paradise.hospital.vo.hosp.HospitalSetQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(tags = "医院测试管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("findAll")
    public Result findAllHospitalSet(){
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }
    @ApiOperation(value = "逻辑删除医院设置")
    @DeleteMapping("{id}")
    public Result removeHospSet(@PathVariable Long id){
        boolean b = hospitalSetService.removeById(id);
        if(b){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }
    @ApiOperation(value = "分页带条件查询")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required=false) HospitalSetQueryVo hospitalSetQueryVo){
        Page<HospitalSet> page = new Page<>(current,limit);
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        String hoscode = hospitalSetQueryVo.getHoscode();
        String hosname = hospitalSetQueryVo.getHosname();
        if(!StringUtils.isEmpty(hoscode)){
            queryWrapper.eq("hoscode",hoscode);
        }
        if(!StringUtils.isEmpty(hosname)){
            queryWrapper.like("hosname", hosname);
        }
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, queryWrapper);
        return  Result.ok(pageHospitalSet);
    }

    @ApiOperation(value = "添加医院设置")
    @PostMapping("saveHospSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
        hospitalSet.setStatus(1);
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        boolean save = hospitalSetService.save(hospitalSet);
        if (save){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }
    @ApiOperation("根据id获取医院设置")
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    @ApiOperation("修改医院设置")
    @PostMapping("updateHospSet")
    public Result updateHospSet(@RequestBody HospitalSet hospitalSet){
        boolean b = hospitalSetService.updateById(hospitalSet);
        if (b){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @ApiOperation("批量修改医院设置")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idSet){
        boolean b = hospitalSetService.removeByIds(idSet);
        if (b){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @ApiOperation("医院设置锁定和解锁")
    @PutMapping("lockHospSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status){
        HospitalSet byId = hospitalSetService.getById(id);
        byId.setStatus(status);
        boolean b = hospitalSetService.updateById(byId);
        if (b){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }
    @ApiOperation("发送签名秘钥")
    @PutMapping("sendKey/{id}")
    public Result sendHospKey(@PathVariable long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }

}
