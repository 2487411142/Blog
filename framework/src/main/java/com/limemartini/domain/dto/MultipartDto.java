package com.limemartini.domain.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.limemartini.utils.MultipartSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipartDto {

    @JSONField(serializeUsing = MultipartSerializer.class)
    private MultipartFile img;


}
