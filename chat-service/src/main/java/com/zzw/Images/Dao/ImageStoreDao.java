package com.zzw.Images.Dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzw.Images.Entiy.StoreImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
public interface ImageStoreDao extends BaseMapper<StoreImage> {
}
