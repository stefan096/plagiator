package com.ftn.plagiator.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class PdfClass {
		
	public static String saveFile(MultipartFile file, String folderPath) throws IOException {
	   	String retVal = null;
        if (! file.isEmpty()) {
	           byte[] bytes = file.getBytes();
	           String filePrefix = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
	           Path path = Paths.get(getResourceFilePath(folderPath).getAbsolutePath() + File.separator + filePrefix + "-" +file.getOriginalFilename());
	           Files.write(path, bytes);
	           retVal = path.toString();
        }
        return retVal;
    }
	
	public static File getResourceFilePath(String path) {
	    return new File(path);
	}
	
}