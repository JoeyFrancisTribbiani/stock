package com.yy.stock.adaptor.amazon.api.pojo.entity;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yy.stock.common.util.UUIDUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @TableId(value = "id")
    String id;

    public String getId() {
        if (id == null) {
            id = UUIDUtil.getUUIDshort();
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean idIsNULL() {
        return StrUtil.isBlank(id);
    }
}
