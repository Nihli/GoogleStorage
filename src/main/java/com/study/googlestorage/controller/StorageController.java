package com.study.googlestorage.controller;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.study.googlestorage.entity.FileEntity;
import com.study.googlestorage.entity.UserEntity;
import com.study.googlestorage.repository.FileRepository;
import com.study.googlestorage.repository.UserRepository;
import com.study.googlestorage.request.UserRequest;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/storage")
public class StorageController {

    @Value("${app.files}")
    private Resource filepath;

    @Autowired
    private Storage storage;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity saveFile(@RequestBody MultipartFile file, @PathVariable("id") Long id) {
        try {
            Optional<UserEntity> opt_user =  userRepository.findById(id);

            if (opt_user.isEmpty()){
                return new ResponseEntity<>("Usuário não existe.", HttpStatus.BAD_REQUEST);
            }

            file.transferTo(Paths.get("./files/" + file.getOriginalFilename()));

            String bucketName = "test-storage-nihli";

            BlobId blobId = BlobId.of(bucketName, id + "/" + file.getOriginalFilename());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

            File fileToRead = new File(filepath.getFile(), file.getOriginalFilename());
            byte[] data = Files.readAllBytes(Paths.get(fileToRead.toURI()));

            storage.create(blobInfo, data);

            fileToRead.delete();

            FileEntity fileEntity = new FileEntity();
            fileEntity.setName("https://storage.cloud.google.com/test-storage-nihli/"+id + "/" + file.getOriginalFilename());
            fileEntity.setUser(opt_user.get());

            fileRepository.save(fileEntity);

            return new ResponseEntity<>("Arquivo salvo.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao salvar arquivo.", HttpStatus.BAD_GATEWAY);
        }
    }

}
