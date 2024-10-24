package com.limemartini.domain.vo;

import com.limemartini.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenusAndCheckedKeysVo {

    private List<Menu> menus;

    private List<Long> checkedKeys;
}
