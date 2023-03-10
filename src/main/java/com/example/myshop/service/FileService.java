package com.example.myshop.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class FileService {
	
	//파일 업로드
	public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
		UUID uuid = UUID.randomUUID(); //중복되지 않은 랜덤한 파일이름 생성
		
		//이름 저장할때 확장자명은 빼고 저장
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String navedFileName = uuid.toString() + extension;
		String fileUploadFullUrl = uploadPath + "/" + navedFileName;
		
		//FileOutputStream 
		//생성자에 파일이 저장될 위치와 파일의 이름을 같이 넘겨 출력 스트림을 만든다.
		FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
		fos.write(fileData); //출력 스트림에 파일 데이터 입력
		fos.close();
		
		return navedFileName;
	
	}
	//파일 삭제
	public void deleteFile(String filePath) throws Exception {
		File deletFile = new File(filePath); //파일이 저장된 경로를 이용해서 파일 객체를 생성
		
		if (deletFile.exists()) { //해당 경로에 파일이 있으면 (ture를 리턴)
			deletFile.delete(); //파일 삭제
			log.info("파일을 삭제하였습니다.");
		} else {
			log.info("파일이 존재하지 않습니다.");
		}
	}
}
