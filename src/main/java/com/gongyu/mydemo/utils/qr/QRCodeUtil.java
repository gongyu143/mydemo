package com.gongyu.mydemo.utils.qr;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import java.util.Map;

/**
 * 二维码工具类
 *
 * @author syb
 * 2020/5/25 10:29
 */
public class QRCodeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(QRCodeUtil.class);
    private static final String CHARSET = "utf-8";
    public static final String JPEG_FORMAT = "jpeg";
    public static final int QUIET_ZONE_SIZE = 4;

    /**
     * 生成二维码
     *
     * @param content    二维码内容
     * @param qrcodeSize 二维码尺寸
     */
    public static BufferedImage generateQRCodeImage(String content, int qrcodeSize) throws Exception {

        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 0);

        BitMatrix bitMatrix = encode(content, BarcodeFormat.QR_CODE, qrcodeSize, qrcodeSize, hints);
        BufferedImage image = toBufferedImage(bitMatrix, qrcodeSize, qrcodeSize);
        //        int width = bitMatrix.getWidth();
//        int height = bitMatrix.getHeight();
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        for (int x = 0; x < width; x++) {
//
//            for (int y = 0; y < height; y++) {
//                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
//            }
//        }
//
//        // 若二维码的实际宽高和预期的宽高不一致, 则缩放
//        if (width != qrcodeSize || width != qrcodeSize) {
//
//            BufferedImage tmp = new BufferedImage(qrcodeSize, qrcodeSize, BufferedImage.TYPE_INT_RGB);
//            tmp.getGraphics().drawImage(image.getScaledInstance(qrcodeSize, qrcodeSize, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
//            image = tmp;
//        }

        return image;
    }

    /**
     * 生成二维码
     *
     * @param content      二维码内容
     * @param imgPath      嵌入二维码图片路径
     * @param needCompress logo是否需要压缩
     * @param qrcodeSize   二维码尺寸
     * @param logoWidth    logo宽度
     * @param logoHeight   logo高度
     */
    public static BufferedImage createImage(String content, String imgPath, boolean needCompress, int qrcodeSize, int logoWidth, int logoHeight) throws Exception {

        BufferedImage codeImg = generateQRCodeImage(content, qrcodeSize);
        if (StringUtils.isBlank(imgPath)) {
            return codeImg;
        }
        // 插入图片
        insertImage(codeImg, imgPath, needCompress, qrcodeSize, logoWidth, logoHeight);
        return codeImg;
    }

    /**
     * 生成二维码
     *
     * @param content      二维码内容
     * @param insertImg    嵌入二维码图片
     * @param needCompress logo是否需要压缩
     * @param qrcodeSize   二维码尺寸
     * @param logoWidth    logo宽度
     * @param logoHeight   logo高度
     */
    private static BufferedImage createImage(String content, BufferedImage insertImg, boolean needCompress, int qrcodeSize, int logoWidth, int logoHeight) throws Exception {

        BufferedImage codeImg = generateQRCodeImage(content, qrcodeSize);
        // 嵌入图片
        if (insertImg != null) {
            QRCodeUtil.insertImage(codeImg, insertImg, needCompress, qrcodeSize, logoWidth, logoHeight);
        }
        return codeImg;
    }

    /**
     * 二维码嵌入图片
     *
     * @param source       二维码图片
     * @param imgPath      嵌入图片文件路径
     * @param needCompress 嵌入图片是否需要压缩
     * @param qrcodeSize   二维码尺寸
     * @param logoWidth    logo宽度
     * @param logoHeight   logo高度
     */
    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress, int qrcodeSize, int logoWidth, int logoHeight) throws Exception {

        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > logoWidth) {
                width = logoHeight;
            }
            if (height > logoWidth) {
                height = logoHeight;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (qrcodeSize - width) / 2;
        int y = (qrcodeSize - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 二维码嵌入图片
     *
     * @param source       源二维码图片
     * @param insertImg    嵌入二维码图片
     * @param needCompress 嵌入二维码图片是否需要压缩
     * @param qrcodeSize   二维码尺寸
     * @param logoWidth    logo宽度
     * @param logoHeight   logo高度
     */
    private static void insertImage(BufferedImage source, BufferedImage insertImg, boolean needCompress, int qrcodeSize, int logoWidth, int logoHeight) throws Exception {

        Image src = insertImg;
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > logoWidth) {
                width = logoHeight;
            }
            if (height > logoWidth) {
                height = logoHeight;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (qrcodeSize - width) / 2;
        int y = (qrcodeSize - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 二维码编码带中心logo
     *
     * @param content      文本内容
     * @param imgPath      嵌入二维码图片
     * @param needCompress 嵌入图片是否需要压缩
     * @param qrcodeSize   二维码尺寸
     * @param logoWidth    logo宽度
     * @param logoHeight   logo高度
     */
    public static BufferedImage encode(String content, String imgPath, boolean needCompress, int qrcodeSize, int logoWidth, int logoHeight) throws Exception {
        BufferedImage image = createImage(content, imgPath, needCompress, qrcodeSize, logoWidth, logoHeight);
        return image;
    }

    /**
     * 二维码编码带中心logo
     *
     * @param content      文本内容
     * @param needCompress 嵌入图片是否需要压缩
     * @param qrcodeSize   二维码尺寸
     * @param logoWidth    logo宽度
     * @param logoHeight   logo高度
     */
    public static BufferedImage encode(String content, boolean needCompress, int qrcodeSize, int logoWidth, int logoHeight) throws Exception {
        BufferedImage codeImg = generateQRCodeImage(content, qrcodeSize);
        return codeImg;
    }

    /**
     * 二维码编码
     *
     * @param content    文本内容
     * @param qrcodeSize 二维码尺寸
     */
    public static BufferedImage encode(String content, int qrcodeSize) throws Exception {
        BufferedImage image = generateQRCodeImage(content, qrcodeSize);
        return image;
    }

    /**
     * 二维码编码(带logo图片)
     *
     * @param content      文本内容
     * @param insertImg    嵌入图片
     * @param needCompress 嵌入图片是否需要压缩
     * @param qrcodeSize   二维码尺寸
     * @param logoWidth    logo宽度
     * @param logoHeight   logo高度
     */
    public static BufferedImage encode(String content, BufferedImage insertImg, boolean needCompress, int qrcodeSize, int logoWidth, int logoHeight) throws Exception {
        BufferedImage image = createImage(content, insertImg, needCompress, qrcodeSize, logoWidth, logoHeight);
        return image;
    }

    /**
     * 二维码解码
     *
     * @param qrcodeImg 二维码图片
     */
    public static String decode(BufferedImage qrcodeImg) throws Exception {

        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(qrcodeImg);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * QRCodeWriter的encode方法,拷贝代码内部调用，以解决白边过多的问题
     * 源码参考 { com.google.zxing.qrcode.QRCodeWriter.encode(QRCode, int, int, int)}
     */
    private static BitMatrix encode(String contents, BarcodeFormat format, int width, int height,
                                    Map<EncodeHintType, ?> hints) throws WriterException {

        if (contents.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }

        if (format != BarcodeFormat.QR_CODE) {
            throw new IllegalArgumentException("Can only encode QR_CODE, but got " + format);
        }

        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Requested dimensions are too small: " + width + 'x' +
                    height);
        }

        ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
        int quietZone = QUIET_ZONE_SIZE;
        if (hints != null) {
            if (hints.containsKey(EncodeHintType.ERROR_CORRECTION)) {
                errorCorrectionLevel = ErrorCorrectionLevel.valueOf(hints.get(EncodeHintType.ERROR_CORRECTION).toString());
            }
            if (hints.containsKey(EncodeHintType.MARGIN)) {
                quietZone = Integer.parseInt(hints.get(EncodeHintType.MARGIN).toString());
            }
        }

        // 二维码生成
        QRCode code = Encoder.encode(contents, errorCorrectionLevel, hints);
        // 输出渲染
        return renderResult(code, width, height, quietZone);
    }

    /**
     * 对 zxing 的 QRCodeWriter 进行扩展, 解决白边过多的问题
     * 源码参考 { com.google.zxing.qrcode.QRCodeWriter.renderResult(QRCode, int, int, int)}
     *



     * @param quietZone 取值 [0, 4]
     */
    private static BitMatrix renderResult(QRCode code, int width, int height, int quietZone) {

        ByteMatrix input = code.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }

        // xxx 二维码宽高相等, 即 qrWidth == qrHeight
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth + (quietZone * 2);
        int qrHeight = inputHeight + (quietZone * 2);

        // 白边过多时, 缩放
        int minSize = Math.min(width, height);
        int scale = calculateScale(qrWidth, minSize);
        if (scale > 0) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("qrCode scale enable! scale: {}, qrSize:{}, expectSize:{}x{}", scale, qrWidth, width, height);
            }

            int padding, tmpValue;
            // 计算边框留白
            padding = (minSize - qrWidth * scale) / QUIET_ZONE_SIZE * quietZone;
            tmpValue = qrWidth * scale + padding;
            if (width == height) {
                width = tmpValue;
                height = tmpValue;
            } else if (width > height) {
                width = width * tmpValue / height;
                height = tmpValue;
            } else {
                height = height * tmpValue / width;
                width = tmpValue;
            }
        }

        int outputWidth = Math.max(width, qrWidth);
        int outputHeight = Math.max(height, qrHeight);
        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;

        BitMatrix output = new BitMatrix(outputWidth, outputHeight);

        for (int inputY = 0, outputY = topPadding; inputY < inputHeight; inputY++, outputY += multiple) {
            // Write the contents of this row of the barcode
            for (int inputX = 0, outputX = leftPadding; inputX < inputWidth; inputX++, outputX += multiple) {
                if (input.get(inputX, inputY) == 1) {
                    output.setRegion(outputX, outputY, multiple, multiple);
                }
            }
        }

        return output;
    }


    /**
     * 如果留白超过5% , 则需要缩放
     * (5% 可以根据实际需要进行修改)
     *
     * @param qrCodeSize 二维码大小
     * @param expectSize 期望输出大小
     * @return 返回缩放比例, <= 0 则表示不缩放, 否则指定缩放参数
     */
    private static int calculateScale(int qrCodeSize, int expectSize) {

        if (qrCodeSize >= expectSize) {
            return 0;
        }

        int scale = expectSize / qrCodeSize;
        int abs = expectSize - scale * qrCodeSize;
        if (abs < expectSize * 0.05) {
            return 0;
        }

        return scale;
    }

    /**
     * 输出二维码图片
     */
    private static BufferedImage toBufferedImage(BitMatrix matrix, int width, int height) {

        int qrCodeWidth = matrix.getWidth();
        int qrCodeHeight = matrix.getHeight();
        BufferedImage qrCode = new BufferedImage(qrCodeWidth, qrCodeHeight, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < qrCodeWidth; x++) {
            for (int y = 0; y < qrCodeHeight; y++) {
                qrCode.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        // 若二维码的实际宽高和预期的宽高不一致, 则缩放
        if (qrCodeWidth != width || qrCodeHeight != height) {

            BufferedImage tmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tmp.getGraphics().drawImage(qrCode.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            qrCode = tmp;
        }

        return qrCode;
    }

}
