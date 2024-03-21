package com.example.telros_test_task.service;

import com.example.telros_test_task.model.MyUser;
import com.example.telros_test_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Page<MyUser> findPaginated(int numberPage, int sizePage, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(numberPage - 1, sizePage, sort);
        return userRepository.findAll(pageable);
    }

    public void saveUser(MyUser myUser){
        userRepository.save(myUser);
    }

    public MyUser getUserById(Long id){
        Optional<MyUser> optionalTask = userRepository.findById(id);
        if(optionalTask.isPresent()){
            return optionalTask.get();
        }
        throw new RuntimeException("Task not found for id : " + id);
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }
}
