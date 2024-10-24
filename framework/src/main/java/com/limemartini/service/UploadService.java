package com.limemartini.service;

import com.limemartini.domain.dto.MultipartDto;

public interface UploadService {
    String uploadImg(MultipartDto img);
}
