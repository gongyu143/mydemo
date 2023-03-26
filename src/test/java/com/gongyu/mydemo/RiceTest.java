package com.gongyu.mydemo;


import com.gongyu.mydemo.utils.PoiUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;


@SpringBootTest
@Slf4j
public class RiceTest {

    @Value("${path.a}")
    private String path;

    @Test
    void test() {
        System.out.println("早知道不学java了 ˚‧º·(˚ ˃̣̣̥᷄⌓˂̣̣̥᷅ )‧º·˚");
    }

    @Test
    void docTest() throws Exception {
        File file = new File("E:/demo/a.doc");

        String lists = PoiUtils.getDocAllText(new FileInputStream(file));
        System.out.println(lists);

    }
}
