package com.example.telros_test_task.api;

import com.example.telros_test_task.model.MyUser;
import com.example.telros_test_task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/user")
    public String getHomePage(Model model){
        return findPaginated(1, "FirstName", "asc", model);
    }


    @GetMapping("/user/page/{numberPage}")
    public String findPaginated(@PathVariable(value = "numberPage") int numberPage,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model){
        int pageSize = 10;
        Page<MyUser> userServicePaginated = userService.findPaginated(numberPage, pageSize, sortField, sortDir);
        model.addAttribute("currentPage", numberPage);
        model.addAttribute("totalPages", userServicePaginated.getTotalPages());
        model.addAttribute("totalElements", userServicePaginated.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc");
        model.addAttribute("listUser", userServicePaginated.getContent());
        return "index";
    }
}
