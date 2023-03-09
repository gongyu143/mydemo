package com.gongyu.mydemo.bean.page;

import com.sun.istack.internal.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
/**
 * @author wengwx
 * @date 2023/3/9
 * @des 分页工具类
 */

public class PageParam {

        @Schema(
                title = "当前页号",
                example = "1"
        )
        @Min(1L)
        @NotNull
        private Integer pageNum;
        @Schema(
                title = "当前页数据条数",
                description = "当 pageSize 为零时返回所有数据，pageNum 无效",
                example = "1"
        )
        @Min(0L)
        private Integer pageSize;

        public PageParam() {
        }

        public void setPageNum(final Integer pageNum) {
            this.pageNum = pageNum;
        }

        public void setPageSize(final Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getPageNum() {
            return this.pageNum;
        }

        public Integer getPageSize() {
            return this.pageSize;
        }
}
