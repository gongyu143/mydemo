package com.gongyu.mydemo.bean.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author wengwx
 * @date 2023/3/9
 * @des
 */
@Data
public class UserParam extends PageParam{

    @Schema(title = "模糊查询 key", description = "查找数据编号/名称")
    private String fuzzyKey;
}
