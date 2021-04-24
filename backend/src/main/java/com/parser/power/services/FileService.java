package com.parser.power.services;

import com.parser.power.models.ConvertType;
import com.parser.power.utils.ConverterRouter;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class FileService {

    public byte[] convertFile(MultipartFile file, ConvertType current, ConvertType target) throws Exception {
        String forConvertString = new String(file.getBytes());
        ConverterRouter converterRouter = new ConverterRouter(forConvertString, current);
        String readyString = converterRouter.convertTo(target);
        System.out.println(readyString);
        return readyString.getBytes();
    }
}
