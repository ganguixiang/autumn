package com.ggxspace.autumn.converter;

import com.ggxspace.autumn.enums.MenuTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;


/**
 * 字符串转菜单类型枚举转换器
 * Created by ganguixiang on 2017/10/11.
 */
@Configuration
public class StringToMenuTypeEnumConverter implements Converter<String, MenuTypeEnum> {
    @Override
    public MenuTypeEnum convert(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return MenuTypeEnum.valueOf(s);
    }
}
