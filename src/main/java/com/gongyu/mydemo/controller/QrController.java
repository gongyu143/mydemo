package com.gongyu.mydemo.controller;

import com.gongyu.mydemo.bean.UserDo;
import com.gongyu.mydemo.bean.page.PageResult;
import com.gongyu.mydemo.bean.page.UserParam;
import com.gongyu.mydemo.bean.result.Response;
import com.gongyu.mydemo.dao.UserDao;
import com.gongyu.mydemo.utils.QrCodeUtil;
import com.gongyu.mydemo.utils.qr.QRCodeUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.List;

@RestController
@RequestMapping("/qr")
@Api(tags = "image")
@Slf4j
public class QrController {



    @PostMapping("generate/v2")
    public Response<?> generateV2(@RequestParam("front") int front,
                                  @RequestParam("back") int back,
                                  @RequestParam("content")   String content,
                                  HttpServletResponse servletResponse){
        try {
            QrCodeUtil.createCodeToOutputStream(content,servletResponse.getOutputStream(),front,back);
        }catch (Exception e){

        }
        return Response.success();
    }


    @PostMapping("generate/v1")
    public Response<?> generateV2(  @RequestParam("content")   String content,
                                    @RequestParam("front") int front,
                                    HttpServletResponse servletResponse) throws Exception {

        BufferedImage bufferedImage = QRCodeUtil.generateQRCodeImage(content, front);
        ImageIO.write(bufferedImage, "png", servletResponse.getOutputStream());
        log.info("二维码图片生成到输出流成功...");
        return Response.success();
    }

    @PostMapping("generate/v3")
    public Response<?> generateV3(  @RequestParam("content")   String content,
                                    @RequestParam("front") int front,
                                    @RequestParam("wide") int wide,
                                    @RequestParam("length") int length,
                                    @RequestParam("path") int path,
                                    HttpServletResponse servletResponse) throws Exception {

        BufferedImage bufferedImage = QRCodeUtil.createImage(content,"C:\\Users\\86183\\Desktop\\a.jpg",true,front,wide,length);
        ImageIO.write(bufferedImage, "png", servletResponse.getOutputStream());
        log.info("二维码图片生成到输出流成功...");
        return Response.success();
    }
}
