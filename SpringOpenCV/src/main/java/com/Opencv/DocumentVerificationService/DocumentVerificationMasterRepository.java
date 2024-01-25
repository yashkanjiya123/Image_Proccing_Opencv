package com.Opencv.DocumentVerificationService;

import org.springframework.web.multipart.MultipartFile;

import com.Opencv.DocumentVerificationResponse.SaveDocumentVerificationMasterResponse;


public interface DocumentVerificationMasterRepository {

    SaveDocumentVerificationMasterResponse saveDocumentVerification(MultipartFile file);

}
