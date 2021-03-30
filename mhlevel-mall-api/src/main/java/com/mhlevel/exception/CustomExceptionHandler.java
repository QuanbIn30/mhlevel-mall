package com.mhlevel.exception;

import com.mhlevel.utils.MHLEVELJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author quanbin
 * @date 2021-03-30
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    // 上传文件超过500k，捕获异常MaxUploadSizeExceededException
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public MHLEVELJSONResult handlerMaxUploadFile(MaxUploadSizeExceededException ex){
        return MHLEVELJSONResult.errorMsg("文件上传不能超过500K，请压缩图片或者降低图片质量");
    }
}
