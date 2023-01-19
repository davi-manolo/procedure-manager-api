package com.procedure.manager.domain.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileVo {

    private String title;

    private String contentBase64;

}
