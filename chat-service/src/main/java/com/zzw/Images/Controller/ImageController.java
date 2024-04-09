package com.zzw.Images.Controller;

import com.zzw.Images.Service.ImageService;
import com.zzw.common.Const;
import com.zzw.common.model.RestBean;
import com.zzw.common.model.enums.ImageError;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
public class ImageController {

    @Resource
    ImageService imageService;

    @GetMapping("/images/**")
    public void imageFetch(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        response.setHeader("Content-Type", "image/jpg");
        imageService.fetchImageAvatar(request, response);
    }

    @GetMapping("/cache/**")
    public void CacheImageFetch(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        response.setHeader("Content-Type", "image/jpg");
        imageService.fetchImageCache(request, response);
    }

    @PostMapping("img")
    public  String uploadImg( @RequestParam("file") MultipartFile file,
                              @RequestAttribute(Const.ACCOUNT.ATTR_USER_ID) Integer id) throws IOException {
        if(file.getSize() > 1024 * 100 * 5)
            return RestBean.error(ImageError.IMAGE_TOO_BIG.getCode(),ImageError.IMAGE_TOO_BIG.getError()).ToJSON();
        String url = imageService.uploadImg(file , id);
        if(url != null){
            log.info("上传成功! 大小为:"+ file.getSize());
            return RestBean.success(url).ToJSON();
        }else {
            return RestBean.error(ImageError.UPLOAD_IMAGE_ERROR.getCode(),ImageError.UPLOAD_IMAGE_ERROR.getError()).ToJSON();
        }
    }

    @PostMapping("avatar")
    public  String uploadAvatar( @RequestParam("file") MultipartFile file,
                                 @RequestAttribute(Const.ACCOUNT.ATTR_USER_ID) Integer id) throws IOException {

        if(file.getSize() > 1024 * 100 * 5)
            return RestBean.error(ImageError.IMAGE_TOO_BIG.getCode(),ImageError.IMAGE_TOO_BIG.getError()).ToJSON();

        log.info("正在上传!");

        String url = imageService.uploadAvatar(file , id);

        if(url != null){
            log.info("上传成功! 大小为:"+ file.getSize());
            return RestBean.success(url).ToJSON();
        }else {
            return RestBean.error(ImageError.UPLOAD_IMAGE_ERROR.getCode(),ImageError.UPLOAD_IMAGE_ERROR.getError()).ToJSON();
        }
    }



}
