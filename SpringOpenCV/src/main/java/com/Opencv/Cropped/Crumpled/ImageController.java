package com.Opencv.Cropped.Crumpled;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import nu.pattern.OpenCV;

@Controller
@RequestMapping("/user/")
public class ImageController {
	
	final static Logger logger = LoggerFactory.getLogger(ImageController.class);

	static {
        OpenCV.loadLocally();
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
    
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "index";
        }

        try {
            byte[] bytes = file.getBytes();
            Mat originalImage = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.IMREAD_COLOR);

            // Perform automatic document cropping
            Mat croppedImage = autoDocumentCrop(originalImage);

            MatOfByte croppedImageMatOfByte = new MatOfByte();
            Imgcodecs.imencode(".png", croppedImage, croppedImageMatOfByte);
            byte[] croppedImagBytes = croppedImageMatOfByte.toArray();
            String croppedImage64 = Base64.getEncoder().encodeToString(croppedImagBytes);

            // Check if the cropped image is crumbled
            boolean isCrumbled = isCrumbled(croppedImage);

            // Perform image processing on the cropped image
            Mat crumbledEdges = detectCrumbledEdges(croppedImage);
            
            MatOfByte crumbledEdgesMatOfByte = new MatOfByte();
            Imgcodecs.imencode(".png", crumbledEdges, crumbledEdgesMatOfByte);
            byte[] crumbledImagBytes = crumbledEdgesMatOfByte.toArray();
            String crumbledImage64 = Base64.getEncoder().encodeToString(crumbledImagBytes);

            // Set the processed images and result in the model
            
            model.addAttribute("croppedImage",croppedImage64);
            model.addAttribute("crumbledEdges", crumbledImage64);
            model.addAttribute("isCrumbled", isCrumbled);

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Error processing the image.");
        }

        return "result";
    }

    private Mat autoDocumentCrop(Mat originalImage) {

		/*
		 * // Convert the image to grayscale Mat grayImage = new Mat();
		 * Imgproc.cvtColor(originalImage, grayImage, Imgproc.COLOR_BGR2GRAY);
		 * 
		 * // Apply GaussianBlur to reduce noise Mat blurredImage = new Mat();
		 * Imgproc.GaussianBlur(grayImage, blurredImage, new Size(5, 5), 0);
		 * 
		 * // Apply Canny edge detection Mat edges = new Mat();
		 * Imgproc.Canny(blurredImage, edges, 50, 150);
		 * 
		 * // Find contours in the binary image Mat hierarchy = new Mat(); MatOfPoint
		 * biggestContour = findBiggestContour(edges);
		 * 
		 * // Get the bounding box of the biggest contour Rect boundingBox =
		 * Imgproc.boundingRect(biggestContour);
		 * 
		 * // Crop the image using the bounding box Mat croppedImage = new
		 * Mat(originalImage, boundingBox); return croppedImage;
		 */
    	
        // Convert the image to grayscale
        Mat grayImage = new Mat();
        Imgproc.cvtColor(originalImage, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Apply GaussianBlur to reduce noise
        Mat blurredImage = new Mat();
        Imgproc.GaussianBlur(grayImage, blurredImage, new Size(5, 5), 0);

        // Apply Canny edge detection
        Mat edges = new Mat();
        Imgproc.Canny(blurredImage, edges, 50, 150);

        // Find contours in the binary image
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(edges, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Create a mask for the text regions
        Mat mask = Mat.zeros(edges.size(), CvType.CV_8UC1);

        // Draw contours on the mask
        Imgproc.drawContours(mask, contours, -1, new Scalar(255), -1);

        // Bitwise AND operation to get the text regions
        Mat textRegions = new Mat();
        Core.bitwise_and(grayImage, grayImage, textRegions, mask);
        return textRegions;
        
    }
    
    
    
    private boolean isCrumbled(Mat image) {
    	// Calculate average intensity variation in the grayscale image
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        Scalar mean = Core.mean(grayImage);

        // Adjust the threshold based on your specific use case
        double threshold = 100;
        logger.info("this is cropped"+mean.val[0]);
        return mean.val[0] < threshold;
    }

    private Mat detectCrumbledEdges(Mat image) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Apply GaussianBlur to reduce noise
        Imgproc.GaussianBlur(grayImage, grayImage, new Size(5, 5), 0);

        // Apply Canny edge detection
        Mat edges = new Mat();
        Imgproc.Canny(grayImage, edges, 50, 150);

        return edges;
    }
    
    
}
