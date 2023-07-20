package org.example.shixi.tables.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
    @ExcelProperty({"时间属性","创建时间"})
    @Schema(description = "创建时间")
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;
    @ExcelProperty({"时间属性","更新时间"})
    @Schema(description = "更新时间")
    @TableField(fill= FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
