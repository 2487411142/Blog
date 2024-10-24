package com.limemartini.utils;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;
import com.limemartini.domain.dto.MultipartDto;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;

public class MultipartSerializer implements ObjectWriter<MultipartFile> {

        @Override
        public void write(JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {
            System.out.println("--invoke MultipartSerializer--");

            if (object == null) {
                jsonWriter.writeNull();
            }
            MultipartFile file = (MultipartFile) object;
            jsonWriter.writeString(file.getOriginalFilename());
        }
    }