/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.liaochong.myexcel.core.converter;

import com.github.liaochong.myexcel.utils.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author liaochong
 * @version 1.0
 */
public class WriteConverterContext {

    private static final List<WriteConverter> WRITE_CONVERTER_CONTAINER = new ArrayList<>();

    public synchronized WriteConverterContext registering(WriteConverter... writeConverters) {
        Objects.requireNonNull(writeConverters);
        Collections.addAll(WRITE_CONVERTER_CONTAINER, writeConverters);
        return this;
    }

    public static Object convert(Field field, Object object) {
        Object result = ReflectUtil.getFieldValue(object, field);
        for (WriteConverter writeConverter : WRITE_CONVERTER_CONTAINER) {
            result = writeConverter.convert(field, result);
        }
        return result;
    }
}
