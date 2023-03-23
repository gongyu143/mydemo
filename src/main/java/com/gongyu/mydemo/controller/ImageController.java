package com.gongyu.mydemo.controller;


import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
@RestController
@RequestMapping("/image")
@Api(tags = "image")
public class ImageController {

    @GetMapping(value = "/img",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage() throws Exception {
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File("D:/test/2.jpg")));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpeg", out);
        return out.toByteArray();
    }

    @GetMapping(value = "/imgs",produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage getImages() throws Exception {
        return ImageIO.read(new FileInputStream(new File("D:/test/2.jpg")));
    }
}
