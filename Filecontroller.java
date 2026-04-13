package com.example.filesharingapp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.filesharingapp.service.FileService;

@Controller
@RequestMapping("/files")
public class Filecontroller {

    @Autowired
    private FileService fileService;

    // ✅ Handle /files → redirect to /files/home (NO LOOP)
    @GetMapping("")
    public String home() {
        return "redirect:/files/home";
    }

    // ✅ Main page
   
   
    @GetMapping("/home")
    public String index(final Model model) {
        model.addAttribute("files", fileService.getAll());
        return "list-files";   // templates/list-files.html
    }

    // ✅ Upload file
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") final MultipartFile file,
                             @RequestParam("uploadedBy") final String uploadedBy) throws IOException {

        fileService.uploadFile(file, uploadedBy);
        return "redirect:/files/home";
    }

    // ✅ Share file page
    @GetMapping("/share/{id}")
    public String shareFile(@PathVariable final int id, final Model model) {

        final ResponseEntity<?> fileModel = fileService.shareFile(id);

        if (fileModel.hasBody()) {
            final String currentUrl = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .toUriString();

            model.addAttribute("shareUrl", currentUrl);
            model.addAttribute("file", fileModel.getBody());

            return "share-file";   // templates/share-file.html
        } else {
            return "redirect:/files/home";
        }
    }

    // ✅ Delete file
    @PostMapping("/delete/{id}")
    public String deleteFile(@PathVariable final int id) {

        fileService.deleteFile(id);
        return "redirect:/files/home";
    }

    // ✅ Download file
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable final int id) {
        return fileService.getFile(id);
    }
}