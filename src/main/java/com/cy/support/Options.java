package com.cy.support;

import com.cy.input.Aria2cOptions;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * 将用户的Options包装一下
 *
 * @author CY
 */
public class Options extends HashMap<String, Object> {

    private Options() {
    }

    /**
     * 获取一个empty选项
     *
     * @return option
     */
    public static Options empty() {
        return of(null);
    }

    /**
     * 将用户的options转换成Map
     *
     * @param options 用户的Options
     * @return option
     */
    public static Options of(Aria2cOptions options) {
        Options option = new Options();
        if (options == null) {
            return option;
        }
        Field[] fields = options.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(options);
                if (value == null) {
                    continue;
                }
                String key = field.getAnnotation(OptionName.class).value();
                option.put(key, value);
            } catch (IllegalAccessException ignored) {
            }
        }
        return option;
    }
}
