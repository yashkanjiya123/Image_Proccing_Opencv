package com.Opencv.DocumentVerificationController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Opencv.DocumentVerificationhandler.DocumentVerificationMasterResourceHandler;
import com.Opencv.constant.APIRequestURL;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping(APIRequestURL.DOCUMENT_BASE_URL)
public class DocumentVerificationMasterController {

    @Autowired
    private DocumentVerificationMasterResourceHandler documentVerificationMasterResourceHandler;


    @PostMapping(APIRequestURL.DOCUMENT_VERIFICATION_POST_URL)
    public ResponseEntity<JsonNode> saveDocumentVerificationconstroller(@RequestHeader HttpHeaders headers,@RequestPart("file") MultipartFile file ) 
    {
    	return documentVerificationMasterResourceHandler.saveDocumentVerification(headers,file);
    }

}
