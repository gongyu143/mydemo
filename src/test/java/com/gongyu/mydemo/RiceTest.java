package com.gongyu.mydemo;


import com.gongyu.mydemo.bean.file.Sheet;
import com.gongyu.mydemo.utils.PoiUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Slf4j
public class RiceTest {

    @Test
    void test() {
        System.out.println("早知道不学java了 ˚‧º·(˚ ˃̣̣̥᷄⌓˂̣̣̥᷅ )‧º·˚");
    }

    @Test
    void docTest() throws Exception {
//        File file = new File("E:/demo/a.doc");
//
//        String lists = PoiUtils.getDocAllText(new FileInputStream(file));
//        System.out.println("doc 输出： "+lists);
        int port = 8080;

        String add = port+"/"+"/api/asb";
        String replaceAll = add.trim().replaceAll("//", "/");
        System.out.println(replaceAll);
    }

    @Test
    void xlsTest() throws Exception {
        String path = "E:/demo/a.xls";
        List<String> result = new ArrayList<>();
        File file = new File(path);
        String format = path.substring(path.lastIndexOf(".") + 1);

        List<String> temp = new ArrayList<>();
        PoiUtils.getExcelAllText(new FileInputStream(file), format).getContent()
                .stream()
                .map(Sheet::getData)
                .forEach(d -> d.forEach(temp::addAll));
        result.addAll(temp);


        System.out.println("xls 输出： "+result);

    }
}
