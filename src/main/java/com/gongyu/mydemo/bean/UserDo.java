package com.gongyu.mydemo.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author wengwx
 * @date 2023/3/9
 * @des
 */

@Data
@Entity
@Table(name = "user")
public class UserDo {

    @ApiModelProperty(value = "修改时间", hidden = false, example = "")
    @Column(name = "ts")
    private Timestamp ts;

    @ApiModelProperty(value = "id", hidden = false, example = "")
    @Column(name = "id")
    private String id;


    @ApiModelProperty(value = "姓名", hidden = false, example = "")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "年龄", hidden = false, example = "")
    @Column(name = "age")
    private int age;


    @ApiModelProperty(value = "备注", hidden = false, example = "")
    @Column(name = "remark")
    private String remark;

    @ApiModelProperty(value = "创建者id", hidden = false, example = "")
    @Column(name = "creator_id")
    private String creatorId;

    @ApiModelProperty(value = "创建者名称", hidden = false, example = "")
    @Column(name = "creator_name")
    private String creatorName;

    @ApiModelProperty(value = "添加时间", hidden = false, example = "")
    @Column(name = "gmt_create")
    private LocalDateTime gmtCreate;
}
