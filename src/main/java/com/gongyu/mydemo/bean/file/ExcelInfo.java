package com.gongyu.mydemo.bean.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * @author  wenxue
 * @date    2023/3/26 21:55
 * @desc
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelInfo {

    private List<Sheet> content;
}
