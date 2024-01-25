package com.Opencv.Crumbled;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user/")
public class DocumentVerificationController {

	@Autowired
    private DocumentVerificationService documentVerificationService;
	
	@PostMapping("/verify-document")
	    public String verifyDocument(@RequestParam("file") MultipartFile file) {
	        try {
	            // Convert MultipartFile to OpenCV Mat
	            byte[] bytes = file.getBytes();
	            Mat image = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.IMREAD_UNCHANGED);

	            // Perform document verification
	            boolean result = documentVerificationService.isHandwritten(image);

	            // Return the result as a response
	            if (result) {
	                return "Document is handwritten.";
	            } else {
	                return "Document is digital.";
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Error processing the document.";
	        }
	    }
}
