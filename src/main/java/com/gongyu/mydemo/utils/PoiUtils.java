package com.gongyu.mydemo.utils;

import com.gongyu.mydemo.bean.file.ExcelInfo;
import com.gongyu.mydemo.bean.file.Sheet;
import com.gongyu.mydemo.bean.result.ResponseCode;
import com.gongyu.mydemo.enums.BaseException;
import com.google.common.collect.Lists;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PoiUtils {

    private static final Integer DPI = 100;
    private static final String IMG_TYPE = "png";

    private PoiUtils() {
    }

    /**
     * 读取文档数据
     *
     * @param format      文档格式
     * @param inputStream stream
     * @return 文旦数据信息
     * @throws IOException io exception
     */
    public static List<String> parseDocuments(String format,
                                              InputStream inputStream) throws IOException {
        List<String> result = new ArrayList<>();
        switch (format.toLowerCase()) {
            case "doc":
                result = Lists.newArrayList(getDocAllText(inputStream));
                break;
            case "docx":
                result = Lists.newArrayList(getDocxAllText(inputStream));
                break;
            case "xlsx":
            case "xls":
                List<String> temp = new ArrayList<>();
                getExcelAllText(inputStream, format).getContent()
                        .stream()
                        .map(Sheet::getData)
                        .forEach(d -> d.forEach(temp::addAll));
                result.addAll(temp);
                break;
            case "ppt":
                result = getPptAllText(inputStream);
                break;
            case "pptx":
                result = getPptXmlAllText(inputStream);
                break;
            case "txt":
                result = Lists.newArrayList(getText(inputStream).replace("\0", ""));
                break;
            default:
                throw new BaseException(ResponseCode.FILE_PARSE_ERROR, "文件解析异常");
        }
        return result;
    }

    //word

    /**
     * 读取 doc 格式所有文本 不分段落
     *
     * @param inputStream stream
     * @return 文本
     * @throws IOException io exception
     */
    public static String getDocAllText(InputStream inputStream) throws IOException {
        HWPFDocument hwpfDocument = new HWPFDocument(inputStream);
        return hwpfDocument.getDocumentText();
    }

    /**
     * 读取 doc 格式所有文本 分段
     *
     * @param inputStream stream
     * @return List<String>
     * @throws IOException io exception
     */
    public static List<String> getDocAllParagraph(InputStream inputStream) throws IOException {
        HWPFDocument hwpfDocument = new HWPFDocument(inputStream);
        List<String> result = new ArrayList<>();
        Range range = hwpfDocument.getRange();
        int num = range.numParagraphs();
        for (int i = 0; i < num; i++) {
            Paragraph paragraph = range.getParagraph(i);
            result.add(paragraph.text());
        }
        return result;
    }

    /**
     * 读取 docx 格式所有文本 不分段落
     *
     * @param inputStream stream
     * @return 文本
     * @throws IOException io exception
     */
    public static String getDocxAllText(InputStream inputStream) throws IOException {
        XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
        XWPFWordExtractor extractor = new XWPFWordExtractor(xwpfDocument);
        return extractor.getText();
    }

    /**
     * 读取 docx 格式所有文本 分段
     *
     * @param inputStream stream
     * @return List<String>
     * @throws IOException io exception
     */
    public static List<String> getDocxAllParagraph(InputStream inputStream) throws IOException {
        XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
        List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
        return paragraphs.stream()
                .map(XWPFParagraph::getText)
                .collect(Collectors.toList());
    }

    //excel

    /**
     * 解析 xlsx 和 xls
     *
     * @param inputStream stream
     * @param fileFormat  文件后缀（format)
     * @return workbook
     * @throws IOException io exception
     */
    public static Workbook getExcel(InputStream inputStream, String fileFormat) throws IOException {
        switch (fileFormat) {
            case "xlsx":
                return new XSSFWorkbook(inputStream);
            case "xls":
                return new HSSFWorkbook(inputStream);
            default:
                throw new BaseException(ResponseCode.FILE_PARSE_ERROR, "格式解析异常: " + fileFormat);
        }
    }

    /**
     * 读取 excel 这种所有的表格数据
     * 支持 xlsx xls
     *
     * @param inputStream stream
     * @param fileFormat  文件后缀(format)
     * @return ExcelInfo
     * @throws IOException io exception
     */
    public static ExcelInfo getExcelAllText(InputStream inputStream, String fileFormat) throws IOException {
        Workbook workbook = getExcel(inputStream, fileFormat);
        int numberOfSheets = workbook.getNumberOfSheets();
        ExcelInfo excelInfo = new ExcelInfo(new ArrayList<>());
        for (int sheetIndex = 0; sheetIndex < numberOfSheets; sheetIndex++) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(sheetIndex);
            Sheet sheetInfo = new Sheet(readShell(sheet));
            excelInfo.getContent().add(sheetInfo);
        }
        return excelInfo;
    }

    /**
     * 读取 excel 中的某个 shell 页
     *
     * @param sheet sheet
     * @return List<List < String>> excel 表格数据
     */
    public static List<List<String>> readShell(org.apache.poi.ss.usermodel.Sheet sheet) {
        List<List<String>> rowList = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum();
        for (int rowNum = 0; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            short lastCellNum = row.getLastCellNum();
            List<String> cellList = new ArrayList<>();
            for (int cellNum = 0; cellNum < lastCellNum; cellNum++) {
                cellList.add(getCellStringVal(row.getCell(cellNum)));
            }
            rowList.add(cellList);
        }
        return rowList;
    }

    /**
     * 读取 excel 某个单元格的数据
     *
     * @param cell 某个单元格
     * @return String
     */
    public static String getCellStringVal(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        String result;
        switch (cellType) {
            case NUMERIC:
                result = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING:
                result = cell.getStringCellValue();
                break;
            case BOOLEAN:
                result = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                result = cell.getCellFormula();
                break;
            case ERROR:
                result = String.valueOf(cell.getErrorCellValue());
                break;
            case BLANK:
            default:
                return "";
        }
        return result;
    }

    //pdf

    /**
     * pdf 中的全部页面转化为 png
     *
     * @param inputStream stream
     * @return List<byte [ ]>
     * @throws IOException io exception
     */
    public static List<byte[]> allPdfToImage(InputStream inputStream) throws IOException {
        List<byte[]> result = new ArrayList<>();
        PDDocument pdfDocument = PDDocument.load(inputStream);
        int numberOfPages = pdfDocument.getNumberOfPages();
        PDFRenderer renderer = new PDFRenderer(pdfDocument);
        for (int index = 0; index < numberOfPages; index++) {
            BufferedImage bufferedImage = renderer.renderImageWithDPI(index, DPI);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, IMG_TYPE, out);
            result.add(out.toByteArray());
        }
        return result;
    }

    //ppt

    /**
     * 读取 ppt 中 文本框、图像框 的文字
     *
     * @param inputStream stream
     * @return List<String> list
     * @throws IOException io exception
     */
    public static List<String> getPptAllText(InputStream inputStream) throws IOException {
        List<String> result = new ArrayList<>();
        HSLFSlideShow slideShow = new HSLFSlideShow(inputStream);
        for (HSLFSlide slide : slideShow.getSlides()) {
            for (List<HSLFTextParagraph> txt : slide.getTextParagraphs()) {
                result.addAll(txt.stream()
                        .map(HSLFTextParagraph::toString)
                        .collect(Collectors.toList()));
            }
        }
        return result;
    }

    /**
     * 读取 pptx 中 文本框、图像框 的文字
     *
     * @param inputStream stream
     * @return List<String>
     * @throws IOException io exception
     */
    public static List<String> getPptXmlAllText(InputStream inputStream) throws IOException {
        List<String> result = new ArrayList<>();
        XMLSlideShow ppt = new XMLSlideShow(inputStream);
        for (XSLFSlide slide : ppt.getSlides()) {
            for (XSLFShape sh : slide.getShapes()) {
                if (sh instanceof XSLFTextShape) {
                    XSLFTextShape shape = (XSLFTextShape) sh;
                    shape.getText();
                    result.add(shape.getText());
                }
            }
        }
        return result;
    }

    //other

    /**
     * 读取 txt 中的文件数据
     *
     * @param inputStream stream
     * @return txt
     * @throws IOException io exception
     */
    public static String getText(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] b = new byte[1024];
        while (inputStream.read(b, 0, 1024) != -1) {
            sb.append(new String(b));
        }
        return sb.toString();
    }

    /**
     * 去除富文本的标签 （待强测试)
     *
     * @param content 文本
     * @return 文本
     */
    public static String removeFullLabel(String content) {
        return content.replaceAll("&[a-zA-Z]{1,10};", "")
                .replaceAll("<[^>]*>", "")
                .replaceAll("[(/>)<]", "");
    }

    //csv tsv

    /**
     * 读取  tsv 文件
     *
     * @param inputStream inputStream
     * @return List<List < String>>
     */
    public static List<List<String>> tsvReader(InputStream inputStream) {
        List<List<String>> result = new ArrayList<>();
        TsvParserSettings settings = new TsvParserSettings();
//        settings.getFormat().setLineSeparator("\n");
        TsvParser parser = new TsvParser(settings);
        try {
            parser.iterate(inputStream)
                    .forEach(d -> result.add(Lists.newArrayList(Arrays.asList(d))));
        } catch (Exception e) {
            log.error("解析 tsv 异常", e);
            throw new BaseException(ResponseCode.FILE_PARSE_ERROR, "tsv文件解析异常");
        } finally {
            parser.stopParsing();
        }
        return result;
    }

    /**
     * 读取 csv 文件
     *
     * @param inputStream inputStream
     * @return List<List < String>>
     */
    public static List<List<String>> csvReader(InputStream inputStream) {
        List<List<String>> result = new ArrayList<>();
        CsvParserSettings csvParserSettings = new CsvParserSettings();
//        csvParserSettings.getFormat().setLineSeparator("\n");
        CsvParser parser = new CsvParser(csvParserSettings);
        try {
            parser.iterate(inputStream)
                    .forEach(d -> result.add(Lists.newArrayList(Arrays.asList(d))));
        } catch (Exception e) {
            log.error("解析 csv 异常", e);
            throw new BaseException(ResponseCode.FILE_PARSE_ERROR, "csv文件解析异常");
        } finally {
            parser.stopParsing();
        }
        return result;
    }
}
