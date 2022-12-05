//package com.example.cs222receiptscanner.receiptAPI;
//
//import java.io.File;
//import java.io.IOException;
//
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//
//public class ReceiptOCR {
//    public void addToDB() {
//        String receiptOcrEndpoint = "https://ocr.asprise.com/api/v1/receipt"; // Receipt OCR API endpoint
//
//        // TODO: change this hardcode to an input from camera API
//        File imageFile = new File("receipt.png");
//
//        //System.out.println("=== Java Receipt OCR Demo - Need help? Email support@asprise.com ===");
//
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            HttpPost post = new HttpPost(receiptOcrEndpoint);
//            post.setEntity(MultipartEntityBuilder.create()
//                    .addTextBody("client_id", "TEST")       // Use 'TEST' for testing purpose
//                    .addTextBody("recognizer", "auto")      // can be 'US', 'CA', 'JP', 'SG' or 'auto'
//                    //.addTextBody("ref_no", "ocr_java_123'") // optional caller provided ref code
//                    .addPart("file", new FileBody(imageFile))    // the image file
//                    .build());
//
//            try (CloseableHttpResponse response = client.execute(post)) {
//                // TODO: take the JSON object, and import data into database
//                System.out.println(EntityUtils.toString(response.getEntity())); // Receipt OCR result in JSON
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
////    public static void main(String[] args) throws Exception {
////        String receiptOcrEndpoint = "https://ocr.asprise.com/api/v1/receipt"; // Receipt OCR API endpoint
////
////        // TODO: change this hardcode to an input from camera API
////        File imageFile = new File("receipt.png");
////
////        //System.out.println("=== Java Receipt OCR Demo - Need help? Email support@asprise.com ===");
////
////        try (CloseableHttpClient client = HttpClients.createDefault()) {
////            HttpPost post = new HttpPost(receiptOcrEndpoint);
////            post.setEntity(MultipartEntityBuilder.create()
////                    .addTextBody("client_id", "TEST")       // Use 'TEST' for testing purpose
////                    .addTextBody("recognizer", "auto")      // can be 'US', 'CA', 'JP', 'SG' or 'auto'
////                    //.addTextBody("ref_no", "ocr_java_123'") // optional caller provided ref code
////                    .addPart("file", new FileBody(imageFile))    // the image file
////                    .build());
////
////            try (CloseableHttpResponse response = client.execute(post)) {
////                // TODO: take the JSON object, and import data into database
////                System.out.println(EntityUtils.toString(response.getEntity())); // Receipt OCR result in JSON
////            }
////        }
////    }
//}
