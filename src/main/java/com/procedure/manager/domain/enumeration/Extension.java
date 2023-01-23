package com.procedure.manager.domain.enumeration;

import com.procedure.manager.domain.vo.DataContentForFileVo;
import com.procedure.manager.domain.vo.FileVo;
import com.procedure.manager.service.creator.XlsCreator;
import lombok.Getter;

import static com.procedure.manager.util.DataEncoderUtils.encodeContent;
import static com.procedure.manager.util.DateUtils.getLocalDateTimeFormattedNow;

@Getter
public enum Extension {

    XLS {
        @Override
        public FileVo getFile(DataContentForFileVo dataContentForFileVo) {
            byte[] content = XlsCreator.create(dataContentForFileVo);
            String contentEncoded = encodeContent(content);
            return fileBuilder(contentEncoded);
        }
    },

    PDF {
        @Override
        public FileVo getFile(DataContentForFileVo dataContentForFileVo) {
            return fileBuilder(null);
        }
    };

    private static final String PROCEDURE_REPORT = "Relatorio_Procedimentos";

    public abstract FileVo getFile(DataContentForFileVo dataContentForFileVo);

    private static FileVo fileBuilder(String contentEncoded) {
        return FileVo.builder()
                .title(String.format("%s_%s", PROCEDURE_REPORT, getLocalDateTimeFormattedNow()))
                .contentBase64(contentEncoded)
                .build();
    }

}
