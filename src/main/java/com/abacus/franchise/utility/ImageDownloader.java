package com.abacus.franchise.utility;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageDownloader {
	
	
    public static void downloadFile(String fileURL, String saveDir) throws Exception {

        // Create target directory
        Files.createDirectories(Paths.get(saveDir));

        // Extract filename
        String fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);

        // Encode ONLY the filename (important!)
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8")
                .replace("+", "%20");  // spaces must be %20

        // Build the final valid URL
        String base = fileURL.substring(0, fileURL.lastIndexOf("/") + 1);
        String encodedURL = base + encodedFileName;

        System.out.println("Encoded URL: " + encodedURL);

        URL url = new URL(encodedURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.connect();

        if (conn.getResponseCode() != 200) {
        	
        }else {

        String savePath = saveDir + "/" + fileName;

        try (InputStream in = conn.getInputStream();
             FileOutputStream out = new FileOutputStream(savePath)) {

            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        }

        System.out.println("Downloaded: " + savePath);
        }
    }
    
}
	
//	public static void downloadImage(String fileURL, String saveDir) throws IOException {
//
//	    Files.createDirectories(Paths.get(saveDir));
//
//	    String fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
//	    String savePath = saveDir + "/" + fileName;
//
//	    URL url = new URL(fileURL.replace(" ", "%20"));
//	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//	    connection.setRequestProperty("User-Agent", "Mozilla/5.0");
//	    connection.connect();
//
//	    try (InputStream in = connection.getInputStream();
//	         FileOutputStream out = new FileOutputStream(savePath)) {
//
//	        byte[] buffer = new byte[8192];
//	        int length;
//
//	        while ((length = in.read(buffer)) != -1) {
//	            out.write(buffer, 0, length);
//	        }
//
//	        System.out.println("Downloaded successfully: " + savePath);
//	    }
//	}

	
	
//    public static void downloadImage(String fileURL, String saveDir) throws IOException {
//
//        // Create directory if it does not exist
//        Files.createDirectories(Paths.get(saveDir));
//
//        // Extract file name from URL
//        String fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
//
//        // Full path where file will be saved
//        String savePath = saveDir + "/" + fileName;
//
//        try (BufferedInputStream in = new BufferedInputStream(new URL(fileURL).openStream());
//             FileOutputStream fileOutputStream = new FileOutputStream(savePath)) {
//
//            byte dataBuffer[] = new byte[1024];
//            int bytesRead;
//
//            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
//                fileOutputStream.write(dataBuffer, 0, bytesRead);
//            }
//
//            System.out.println("Download complete: " + savePath);
//        }
//    }

  

