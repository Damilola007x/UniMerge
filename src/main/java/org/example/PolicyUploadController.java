package org.example;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import java.util.Base64;
import java.io.IOException;

@RestController
@RequestMapping("/api/digital-judge")
public class PolicyUploadController {

    @PostMapping("/analyze-handbook")
    public ResponseEntity<String> uploadHandbook(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !file.getContentType().equals("application/pdf")) {
            return ResponseEntity.badRequest().body("Please upload a valid PDF file.");
        }

        try {
            // 1. Convert PDF bytes to Base64
            byte[] fileContent = file.getBytes();
            String base64Encoded = Base64.getEncoder().encodeToString(fileContent);

            // 2. Pass this string to your Gemini Service
            // String result = geminiService.extractRules(base64Encoded);

            return ResponseEntity.ok("File processed successfully. Base64 length: " + base64Encoded.length());

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error reading file: " + e.getMessage());
        }
    }
}