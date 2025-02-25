package org.example.restexam2.service;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.example.restexam2.domain.User;
import org.example.restexam2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;


    public List<User> findAll() {
      return userRepository.findAll();
    }


    @Transactional(readOnly = true)
    public User getUser(Long id){

        return userRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("id에 해당하는 사용자를 찾을 수 없어요. id::"+id));
    }

    @Transactional
    public User save(User user) {

        try{
            User savedUser = userRepository.save(user);
            return savedUser;

        }catch (Exception e){
            throw  new RuntimeException("사용자 추가 실패 : " + e.getMessage());
        }


    }

    @Transactional
    public User update(User user) {


        if(user.getId() == null){
            throw new IllegalArgumentException("사용자가 없다");
        }

         User findUser =  userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("사용자 없다"));

        if(user.getName() != null){
            findUser.setName(user.getName());
        }
        if(user.getEmail() != null){
            findUser.setEmail(user.getEmail());
        }
        return findUser; // 커밋될때.. findUser의 변경감지로 자동 업데이트 진행될 것임
        // save()를 호출하는 것보다는 변경감지 해서 수정하도록 하는것이 좋다


    }


    @Transactional
    public Boolean delete(Long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw  new RuntimeException(id + " 사용자 삭제 실패 : " + e.getMessage());
        }

    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }







}
