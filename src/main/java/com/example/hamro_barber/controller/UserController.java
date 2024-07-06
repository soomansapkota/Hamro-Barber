package com.example.hamro_barber.controller;

import com.example.hamro_barber.helper.ApiResponse;
import com.example.hamro_barber.mapper.UserMapper;
import com.example.hamro_barber.model.dto.PasswordChangeDto;
import com.example.hamro_barber.model.validation.ImageFileValidator;
import com.example.hamro_barber.model.validation.ValidImageFile;
import com.example.hamro_barber.service.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final ResourceLoader resourceLoader;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(userMapper.listUserToDto(userService.findAllUsers()), HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getUserFromUserId(@PathVariable Integer userId) {
        return new ResponseEntity<>(userMapper.userToDto(userService.findUserById(userId)), HttpStatus.OK);
    }



    @GetMapping("/get-logged-in-user")
    public ResponseEntity<?> getLoggedInUser(Principal principal) {
        return new ResponseEntity<>(principal.getName(), HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordChangeDto passwordChangeDto, Principal principal) {
        return new ResponseEntity<>(userService.updatePassword(passwordChangeDto, principal.getName()), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/image/save")
    @Validated
    @ValidImageFile
    public ResponseEntity<?> saveImage( @RequestPart("file") @NotNull MultipartFile file) {
        System.out.println("File size: " + file.getSize());
        userService.saveImage(file);
        ApiResponse apiResponse = new ApiResponse(true, "Image uploaded");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{userId}/get-image")
    public ResponseEntity<?> getImage(@PathVariable Integer userId, HttpServletResponse response) throws IOException {
        userService.findUserById(userId);
        MediaType mediaType = MediaType.IMAGE_JPEG;
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(userService.loadImage(userId));
    }
}
