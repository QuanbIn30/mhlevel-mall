package com.mhlevel.controller.center;

import com.mhlevel.controller.BaseController;
import com.mhlevel.pojo.Users;
import com.mhlevel.pojo.bo.center.CenterUserBO;
import com.mhlevel.resource.FileUpload;
import com.mhlevel.service.center.CenterUserService;
import com.mhlevel.utils.CookieUtils;
import com.mhlevel.utils.DateUtil;
import com.mhlevel.utils.JsonUtils;
import com.mhlevel.utils.MHLEVELJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;

    /**
     * 修改用户信息
     * @param userId
     * @param centerUserBO
     * @param result
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/update")
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
     * 用户头像修改
     * @param userId
     * @param file
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value="用户头像修改", notes = "用户头像修改", httpMethod = "POST")
    @PostMapping("/uploadFace")
    public MHLEVELJSONResult uploadFace(
            @ApiParam(name="userId", value="用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "file", value= "用户头像", required = true)
            MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response){

        // 定义头像保存地址
        //String fileSpace = IMAGE_USER_FACE_LOCATION;
        String fileSpace = fileUpload.getImageUserFaceLocation();
        // 在路径上为每个用户增加一个userId,用于区分不同的用户上传
        String uploadPathPrefix = File.separator + userId;
        // 开始文件上传
        if (file != null) {
            FileOutputStream fileOutputStream = null;
            try {
            // 获得文件上传的文件名称
            String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    // 文件重命名
                    String[] fileNameArr = fileName.split("\\.");

                    // 获取文件后缀名
                    String suffix = fileNameArr[fileNameArr.length - 1];

                    if (suffix.equalsIgnoreCase("png")
                        && suffix.equalsIgnoreCase("jpg")
                        && suffix.equalsIgnoreCase("jpeg")){
                        return MHLEVELJSONResult.errorMsg("图片格式不正确");
                    }

                    // face-{userId}.png
                    // 文件名重组
                    String newFileName = "face-" + userId + "." + suffix;

                    // 上传的头像最终保存的位置
                    String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;
                    // 用于提供给web服务访问的地址
                    uploadPathPrefix += ("/" + newFileName);

                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null) {
                        // 创建文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    // 文件输出保存到目录
                    fileOutputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);

                }
            }catch(IOException e){
                e.printStackTrace();
            }finally {

                try{
                    if (fileOutputStream != null){
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                }catch(IOException e2){
                    e2.printStackTrace();
                }
            }
        }else {
            return MHLEVELJSONResult.errorMsg("文件不能为空");
        }

        //获取访问用户头像的地址
        String userFaceURL = fileUpload.getImageServelUrl();

        // 由于浏览器可能存在缓存的情况，所以在这里，我们需要加上时间戳来保证更新后的图片可即使刷新
        String finalUserFaceUrl = userFaceURL + uploadPathPrefix
                + "?t=" + DateUtil.getCurrentDateString("yyyyMMddHHmmss");

        // 更新用户头像到数据库
        Users userResult = centerUserService.updateUserFace(userId, finalUserFaceUrl);

        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);
        // TODO 后续要改，增加令牌TOKEN，会整合进redis，分布式会话

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
