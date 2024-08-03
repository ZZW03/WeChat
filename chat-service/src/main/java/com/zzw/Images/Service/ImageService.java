package com.zzw.Images.Service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.Images.Entiy.StoreImage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;

public interface ImageService extends IService<StoreImage> {

    String uploadAvatar(MultipartFile file , int id) throws IOException;
    String uploadImg(MultipartFile file , int id) throws IOException;

    void fetchImageFromMinio(OutputStream stream, String image) throws Exception;

    void fetchImageFromCache(OutputStream stream, String image) throws Exception;

     void fetchImageCache(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception;

    public void fetchImageAvatar(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception;
}
