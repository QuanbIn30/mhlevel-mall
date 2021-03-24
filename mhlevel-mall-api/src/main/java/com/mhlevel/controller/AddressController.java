package com.mhlevel.controller;

import com.mhlevel.pojo.UserAddress;
import com.mhlevel.pojo.bo.AddressBO;
import com.mhlevel.service.AddressService;
import com.mhlevel.utils.MHLEVELJSONResult;
import com.mhlevel.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author quanbin
 * @date 2021-03-24
 */
@Api(value = "地址相关", tags = {"地址相关的api接口"})
@RequestMapping("address")
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    @PostMapping("/add")
    public MHLEVELJSONResult add(@RequestBody AddressBO addressBO){
        MHLEVELJSONResult checkResult = checkAddress(addressBO);
        if (checkResult.getStatus() != 200){
            return checkResult;
        }

        addressService.AddNewUserAddress(addressBO);

        return MHLEVELJSONResult.ok();
    }

    @ApiOperation(value = "根据用户id查询收货列表", notes = "根据用户id查询收货列表", httpMethod = "GET")
    @GetMapping("/list")
    public MHLEVELJSONResult list(@RequestParam String userId){
        if (userId == null) {
            return MHLEVELJSONResult.errorMsg("");
        }
        List<UserAddress> userAddressList = addressService.QueryAll(userId);
        return MHLEVELJSONResult.ok(userAddressList);
    }

    @ApiOperation(value = "根据用户id和地址id删除地址", notes = "根据用户id和地址id删除地址", httpMethod = "GET")
    @GetMapping("/delete")
    public MHLEVELJSONResult delete(@RequestParam String userId, @RequestParam String userAddressId){

        if(StringUtils.isBlank(userId) || StringUtils.isBlank(userAddressId)){
            return MHLEVELJSONResult.errorMsg("");
        }
        addressService.DeleteUserAddress(userId, userAddressId);
        return MHLEVELJSONResult.ok();
    }

    @ApiOperation(value = "根据用户id和地址id设置默认地址", notes = "根据用户id和地址id设置默认地址", httpMethod = "GET")
    @GetMapping("/setDefault")
    public MHLEVELJSONResult setDefault(@RequestParam String userId, @RequestParam String userAddressId){

        if(StringUtils.isBlank(userId) || StringUtils.isBlank(userAddressId)){
            return MHLEVELJSONResult.errorMsg("");
        }
        addressService.UpdateUserAddressToBeDefault(userId, userAddressId);
        return MHLEVELJSONResult.ok();
    }

    private MHLEVELJSONResult checkAddress(AddressBO addressBO){
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)){
            return MHLEVELJSONResult.errorMsg("收货人不能为空");
        }

        if (receiver.length() > 12){
            return MHLEVELJSONResult.errorMsg("收货人姓名不能太长");
        }
        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)){
            return MHLEVELJSONResult.errorMsg("手机号码不能为空");
        }
        if (mobile.length() != 11){
            return MHLEVELJSONResult.errorMsg("手机号码长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk){
            return MHLEVELJSONResult.errorMsg("手机号码格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
            StringUtils.isBlank(city) ||
            StringUtils.isBlank(district) ||
            StringUtils.isBlank(detail)){
            return MHLEVELJSONResult.errorMsg("收货地址信息不能为空");
        }
        return MHLEVELJSONResult.ok();
    }
}
