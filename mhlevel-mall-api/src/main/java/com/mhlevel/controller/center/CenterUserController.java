package com.mhlevel.controller.center;

import com.mhlevel.pojo.Users;
import com.mhlevel.pojo.bo.center.CenterUserBO;
import com.mhlevel.service.center.CenterUserService;
import com.mhlevel.utils.CookieUtils;
import com.mhlevel.utils.JsonUtils;
import com.mhlevel.utils.MHLEVELJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author quanbin
 * @date 2021-03-29
 */
@Api(value="用户信息接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController {

    @Autowired
    private CenterUserService centerUserService;

    public MHLEVELJSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "centerUserBO", value = "用户对象", required = true)
            @RequestBody @Valid CenterUserBO centerUserBO,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response){

        // 判断BindingResult是否保存有错误的验证信息，如果有，则直接return就可以了
        if(result.hasErrors()){
            Map<String, String> errMap = getError(result);
            return MHLEVELJSONResult.errorMap(errMap);
        }
        Users resultUser = centerUserService.updateUserInfo(userId, centerUserBO);
        resultUser = setNullProperty(resultUser);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(resultUser), true);

        //TODO 后续要改，增加令牌TOKEN，会整合进redis，分布式会话

        return MHLEVELJSONResult.ok();
    }

    /**
     * 隐藏用户隐私信息
     * @param userResult
     * @return
     */
    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    private Map<String, String> getError(BindingResult result){
        Map<String, String> map = new HashMap<>();
        List<FieldError> errList =  result.getFieldErrors();
        for (FieldError error : errList){
            String field = error.getField();
            String msg = error.getDefaultMessage();
            map.put(field, msg);
        }
        return map;
    }
}
