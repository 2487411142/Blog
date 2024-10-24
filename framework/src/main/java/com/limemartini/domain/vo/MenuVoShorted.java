package com.limemartini.domain.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MenuVoShorted {

    private Long id;

    @JSONField(name = "label")
    private String menuName;

    //parent menu ID
    private Long parentId;

    private List<MenuVoShorted> children;

}
