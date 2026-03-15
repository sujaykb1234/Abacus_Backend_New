package com.abacus.franchise.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;



public class ImageStoreProcess {

	 private static String uploadDir = "C:/Images/";
	
	public static  List<String> saveFile(MultipartFile uploadfile,HttpServletRequest request)  {
		
		List<String> fileDetail = new ArrayList<>();
		
		 if(uploadfile != null && !uploadfile.isEmpty()) {
			 
			 
			 try {
	    		  Files.createDirectories(Paths.get(uploadDir));
	    	      String originalFilename = Objects.requireNonNull(uploadfile.getOriginalFilename()).replaceAll("\\s+", "_");

	    	      String filename = System.currentTimeMillis() + "_" + originalFilename;
	    	      Path filepath = Paths.get(uploadDir, filename);

	    	      uploadfile.transferTo(filepath.toFile());

	    	      String imageUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	    	                          + "/abacus/abacus/v1/Admin/view/" + filename;
	    	     
	    	      fileDetail.add(filename);
	    	      fileDetail.add(imageUrl);
	  			  return fileDetail;

	    	    } catch (IOException e) {
                     System.out.println(e.getMessage());
	    	    }
		
		    }
		 return null;
		
	}
	
	
//	public static boolean deleteFile(String uploadDir, String filename) {
//
//		 try {
//		        Path filePath = Paths.get(uploadDir, filename);
//
//		        if (Files.exists(filePath)) {
//		            Files.delete(filePath);
//		            return true;
//		        } else {
//		            return false;
//		        }
//
//		    } catch (IOException e) {
//		        System.out.println("Error deleting file: " + e.getMessage());
//		        return false;
//		    }
//	}

	
	 public static boolean deleteFile(String imageUrl, String fileName) {
	        try {
	            if (imageUrl != null && imageUrl.contains("/")) {
	                fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
	            }

	            Path filePath = Paths.get(uploadDir, fileName);

	            if (Files.exists(filePath)) {
	                Files.delete(filePath);
	                System.out.println("Deleted: " + filePath);
	                return true;
	            } else {
	                System.out.println("File not found: " + filePath);
	                return false;
	            }

	        } catch (Exception e) {
	            System.out.println("Delete error: " + e.getMessage());
	            return false;
	        }
	    }
	
}
