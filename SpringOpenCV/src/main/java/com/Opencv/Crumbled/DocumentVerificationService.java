package com.Opencv.Crumbled;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DocumentVerificationService {

	 final static Logger logger = LoggerFactory.getLogger(DocumentVerificationController.class);
	
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

	        logger.info("this is Document Verification"+averagePerimeter);
	        // Make the decision
	        return averagePerimeter > perimeterThreshold;
	    }
}
