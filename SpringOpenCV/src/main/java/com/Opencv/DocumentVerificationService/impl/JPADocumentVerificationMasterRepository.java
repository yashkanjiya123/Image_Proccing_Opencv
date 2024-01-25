package com.Opencv.DocumentVerificationService.impl;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Opencv.DocumentVerificationResponse.SaveDocumentVerificationMasterResponse;
import com.Opencv.DocumentVerificationService.DocumentVerificationMasterRepository;

@Service
public class JPADocumentVerificationMasterRepository implements DocumentVerificationMasterRepository {

    

    @Override
    public SaveDocumentVerificationMasterResponse saveDocumentVerification(MultipartFile file) 
    {

        SaveDocumentVerificationMasterResponse saveDocumentVerificationMasterResponse = new SaveDocumentVerificationMasterResponse();

        try {
            // Convert MultipartFile to OpenCV Mat
            byte[] bytes = file.getBytes();
            Mat image = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.IMREAD_UNCHANGED);

            // Perform document verification
            boolean result = isHandwritten(image);

            // Return the result as a response
            if (result) {
            	saveDocumentVerificationMasterResponse.setMessage("DOCUMENT_IS_HANDWRITTEN.");
                return saveDocumentVerificationMasterResponse;
            } else {
            	saveDocumentVerificationMasterResponse.setMessage("DOCUMENT_IS_DIGITAL");
                return saveDocumentVerificationMasterResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
            saveDocumentVerificationMasterResponse.setMessage("ERROR_IN_DOCUMENT");
            return saveDocumentVerificationMasterResponse;
        }
   
    }
    
    public boolean isHandwritten(Mat image) {
        // Convert the image to grayscale
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

        // Apply GaussianBlur to reduce noise and improve Canny edge detection
        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

        // Apply Canny edge detection
        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, 50, 150);

        // Find contours in the edge-detected image
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(edges, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        // Calculate the total perimeter of all contours
        double totalPerimeter = 0;
        for (MatOfPoint contour : contours) {
            totalPerimeter += Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true);
        }

        // Calculate the average perimeter
        double averagePerimeter = totalPerimeter / contours.size();

        // Set a threshold for decision
        double perimeterThreshold = 100; // Adjust based on your analysis

        //logger.info("this is Document Verification"+averagePerimeter);
        // Make the decision
        return averagePerimeter > perimeterThreshold;
    }
    

    

}
