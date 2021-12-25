package com.tsy.yebserver.common.convertor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 日期转换类
 *
 * @author Steven.T
 * @date 2021/12/25
 */
@Slf4j
@Component
public class DateConvertor implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        try {
            return LocalDate.parse(source);
        } catch (Exception exception) {
            log.error("日期转换错误:{}",exception.getMessage());
            return null;
        }
    }

}
