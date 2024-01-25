package com.Opencv.DocumentVerificationhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.Opencv.DocumentVerificationResponse.SaveDocumentVerificationMasterResponse;
import com.Opencv.DocumentVerificationService.DocumentVerificationMasterRepository;
import com.Opencv.helper.HeaderProcessingHelper;
import com.Opencv.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class DocumentVerificationMasterResourceHandler {

    @Autowired
    private DocumentVerificationMasterRepository documentVerificationMasterRepository;

    public ResponseEntity<JsonNode> saveDocumentVerification(HttpHeaders headers,MultipartFile file) 
    {
        HeaderProcessingHelper.setRequestHeaders(file, headers);
//        if (saveDocumentVerificationMasterRequest.checkBadRequest()) {
//            return new ResponseEntity<>(Utils.generateErrorResponse("BAD_REQUEST"), HttpStatus.OK);
//        }
        SaveDocumentVerificationMasterResponse saveDocumentVerificationMasterResponse = documentVerificationMasterRepository.saveDocumentVerification(file);
        return Utils.getJsonNodeResponseEntity(saveDocumentVerificationMasterResponse);
    }

   

}
