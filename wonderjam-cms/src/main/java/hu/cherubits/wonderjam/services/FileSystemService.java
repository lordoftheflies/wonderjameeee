/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author lordoftheflies
 */
@RestController
@RequestMapping("/fs")
public class FileSystemService {

    @Value("${cms.fs.root}")
    private String rootContentFolder = "data";

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public String handleFileUpload(
//            @RequestParam(value = "name", required = false) String name,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
//        if (name.contains("/")) {
//            redirectAttributes.addFlashAttribute("message", "Folder separators not allowed");
//            return "redirect:/";
//        }
//        if (name.contains("/")) {
//            redirectAttributes.addFlashAttribute("message", "Relative pathnames not allowed");
//            return "redirect:/";
//        }

        if (!file.isEmpty()) {
            try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(rootContentFolder + "/" + file.getOriginalFilename())))) {
                FileCopyUtils.copy(file.getInputStream(), stream);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
            redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        } else {
            redirectAttributes.addFlashAttribute("message", "You failed to upload " + file.getOriginalFilename() + " because the file was empty");
        }

        return "redirect:/";
    }

}
