package com.zzw.Images.Service.impl;



import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.Account.Entiy.AccountDetails;
import com.zzw.Account.Service.AccountDetailService;
import com.zzw.Images.Dao.ImageStoreDao;
import com.zzw.Images.Entiy.StoreImage;
import com.zzw.Images.Service.ImageService;
import com.zzw.Utils.FlowUtil;
import com.zzw.common.Const;
import com.zzw.common.model.RestBean;
import com.zzw.common.model.enums.Error;
import com.zzw.common.model.enums.ImageError;
import io.minio.*;
import io.minio.errors.*;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Service
public class ImageServiceimp extends ServiceImpl<ImageStoreDao, StoreImage> implements ImageService {

    @Resource
    FlowUtil flowUtils;

    @Autowired
    MinioClient minioClient;

    @Resource
    AccountDetailService accountDetailService;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    @Override
    public String uploadAvatar(MultipartFile file, int id) throws IOException {
        String imagename = UUID.randomUUID().toString().replace("-","");
        String imageName = "/avatar/"+imagename ;

        PutObjectArgs args =
                PutObjectArgs.builder()
                        .bucket("imchat")
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .object(imageName)
                        .build();

        try {
            minioClient.putObject(args);
            String avatar = accountDetailService.getById(id).getAccountAvatar();
            this.DeleteOldAvatar(avatar);
         if(accountDetailService.update(null,
                 Wrappers.<AccountDetails>update().eq("account_id",id).set("account_avatar",imagename))){
             return imageName;
         }else{
             return null;
         }
        }catch (Exception e){
            return RestBean.error(ImageError.UPLOAD_IMAGE_ERROR.getCode(), ImageError.UPLOAD_IMAGE_ERROR.getError()).ToJSON();
        }

    }

    @Override
    public String uploadImg(MultipartFile file, int id) throws IOException {


        String key = Const.REDIS.IMAGE_UPDATE_BLACK_LIST + id;
        if (!flowUtils.LimitCheck(key,20,3600 ))
            return RestBean.error(ImageError.UPLOAD_TOO_MANY.getCode(), ImageError.UPLOAD_TOO_MANY.getError()).ToJSON();
        String imageName = UUID.randomUUID().toString().replace("-","");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        imageName = "/cache/"+ format +"/"+imageName  ;

        PutObjectArgs args =
                PutObjectArgs.builder()
                        .bucket("imchat")
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .object(imageName)
                        .build();

        try {
            minioClient.putObject(args);
            if(this.save(new StoreImage(id,imageName,date))){
                return imageName;
            }else{
                return null;
            }

        }catch (Exception e){
            return RestBean.error(ImageError.UPLOAD_IMAGE_ERROR.getCode(), ImageError.UPLOAD_IMAGE_ERROR.getError()).ToJSON();
        }

    }

    @Override
    public void fetchImageCache(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        String imagePath = request.getServletPath().substring(7);

        ServletOutputStream stream = response.getOutputStream();

        if(imagePath.length() <= 13) {
            response.setStatus(404);
            stream.println(RestBean.error(Error.NOT_FIND.getCode(), Error.NOT_FIND.getError()).toString());
        } else {
            try {
                this.fetchImageFromCache(stream, imagePath);
                response.setHeader("Cache-Control", "max-age=2592000");
                response.setHeader("Content-Type","image/jpg");

            } catch (ErrorResponseException e) {
                if(e.response().code() == 404) {
                    response.setStatus(404);
                    stream.println(RestBean.error(Error.NOT_FIND.getCode(), Error.NOT_FIND.getError()).toString());
                } else {
                    log.error("从Minio获取图片出现异常: "+e.getMessage(), e);
                }
            }
        }
    }
    @Override
    public void fetchImageAvatar(HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        String imagePath = request.getServletPath().substring(8);
        ServletOutputStream stream = response.getOutputStream();

        if(imagePath.length() <= 13) {
            response.setStatus(404);
            stream.println(RestBean.error(Error.NOT_FIND.getCode(), Error.NOT_FIND.getError()).toString());
        } else {
            try {
                this.fetchImageFromMinio(stream, imagePath);
                response.setHeader("Cache-Control", "max-age=2592000");
                response.setHeader("Content-Type","image/jpg");

            } catch (ErrorResponseException e) {
                if(e.response().code() == 404) {
                    response.setStatus(404);
                    stream.println(RestBean.error(Error.NOT_FIND.getCode(), Error.NOT_FIND.getError()).toString());
                } else {
                    log.error("从Minio获取图片出现异常: "+e.getMessage(), e);
                }
            }
        }
    }

    public void fetchImageFromMinio(OutputStream stream,
                                    String image) throws Exception {
        GetObjectArgs args = GetObjectArgs.builder()
                .bucket("imchat")
                .object("/avatar/"+image)
                .build();

        GetObjectResponse response = minioClient.getObject(args);
        IOUtils.copy(response, stream);
    }

    public void fetchImageFromCache(OutputStream stream,
                                    String image) throws Exception {



        GetObjectArgs args = GetObjectArgs.builder()
                .bucket("imchat")
                .object("/cache/"+image)
                .build();

        GetObjectResponse response = minioClient.getObject(args);
        IOUtils.copy(response, stream);
    }

    private void DeleteOldAvatar(String avatar) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (avatar == null || avatar.isEmpty()) return;
        RemoveObjectArgs remove = RemoveObjectArgs.builder()
                .bucket("imchat")
                .object("/avatar/"+avatar)
                .build();
        minioClient.removeObject(remove);
    }

}
