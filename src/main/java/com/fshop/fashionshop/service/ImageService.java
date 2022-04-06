package com.fshop.fashionshop.service;

import com.fshop.fashionshop.model.Product;
import com.fshop.fashionshop.model.commons.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    Product saveImagesToFolder(long productId, MultipartFile[] images);

    byte[] readAllByProductId(long productId, long imgId) throws IOException;

    Image update(long productId,MultipartFile[] images);

    void delete(MultipartFile file);
}
