package com.gongyu.mydemo.bean.page;

import com.github.pagehelper.Page;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wengwx
 * @date 2023/3/9
 * @des  分页返回
 */
@Schema(
        title = "分页查询结果",
        description = "分页查询结果"
)
public class PageResult<T> {
    @Schema(
            title = "数据总条数",
            description = "如果查询时分页参数中 pageNum 为 0 则该值为结果列表大小"
    )
    private Long total;
    @Schema(
            title = "数据总页数",
            description = "如果查询时分页参数中 pageNum 为 0 则该值为 0|1"
    )
    private Integer pages;
    @Schema(
            title = "当前页号",
            description = "如果查询时分页参数中 pageNum 为 0 则该值为 1"
    )
    private Integer pageNum;
    @Schema(
            title = "当前页数据条数",
            description = "如果查询时分页参数中 pageNum 为 0 则该值为结果列表大小"
    )
    private Integer pageSize;
    @Schema(
            title = "当前页结果列表"
    )
    private List<T> list;

    public PageResult(Long total, Integer pages, Integer pageNum, Integer pageSize, List<T> list) {
        this.total = total;
        this.pages = pages;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.list = list;
    }

    public PageResult(Long total, Integer pages, PageParam pageParam, List<T> list) {
        this.total = total;
        this.pages = pages;
        this.list = list;
        this.pageNum = pageParam.getPageNum();
        this.pageSize = pageParam.getPageSize();
    }

    public PageResult(List<T> list) {
        if (Objects.isNull(list)) {
            this.total = 0L;
            this.pageSize = 0;
            this.pages = 0;
            this.list = new ArrayList(0);
            this.pageNum = 1;
        } else {
            if (list instanceof Page) {
                Page<T> page = (Page)list;
                this.total = page.getTotal();
                this.pages = page.getPages();
                this.pageNum = page.getPageNum();
                this.pageSize = page.getPageSize();
                this.list = page.getResult();
            } else {
                this.total = (long)list.size();
                this.pageSize = list.size();
                this.pages = this.pageSize > 0 ? 1 : 0;
                this.list = list;
                this.pageNum = 1;
            }

        }
    }

    public Long getTotal() {
        return this.total;
    }

    public Integer getPages() {
        return this.pages;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public List<T> getList() {
        return this.list;
    }
}
