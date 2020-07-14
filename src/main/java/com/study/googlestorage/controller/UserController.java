package com.study.googlestorage.controller;

import com.study.googlestorage.entity.UserEntity;
import com.study.googlestorage.repository.UserRepository;
import com.study.googlestorage.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createUser(@Valid @RequestBody UserRequest userRequest){
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(userRequest.getName());

            return new ResponseEntity<>(userRepository.save(userEntity), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Não foi possível criar o usuário.", HttpStatus.BAD_GATEWAY);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getUser(@NotNull @PathVariable("id") Long id){
        try {
            Optional<UserEntity> opt_user = userRepository.findById(id);

            if (opt_user.isEmpty()){
                return new ResponseEntity<>("Usuário não existe.", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(opt_user.get(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Não foi possível localizar o usuário.", HttpStatus.BAD_GATEWAY);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@NotNull @PathVariable("id") Long id, @Valid @RequestBody UserRequest userRequest){
        try {
            Optional<UserEntity> opt_user = userRepository.findById(id);

            if (opt_user.isEmpty()){
                return new ResponseEntity<>("Usuário não existe.", HttpStatus.BAD_REQUEST);
            }

            UserEntity user = opt_user.get();
            user.setName(userRequest.getName());

            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Não foi possível localizar o usuário.", HttpStatus.BAD_GATEWAY);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@NotNull @PathVariable("id") Long id){
        try {
            Optional<UserEntity> opt_user = userRepository.findById(id);

            if (opt_user.isEmpty()){
                return new ResponseEntity<>("Usuário não existe.", HttpStatus.BAD_REQUEST);
            }

            userRepository.delete(opt_user.get());

            return new ResponseEntity<>("Usuário removido.", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Não foi possível remover o usuário.", HttpStatus.BAD_GATEWAY);
        }
    }
}
