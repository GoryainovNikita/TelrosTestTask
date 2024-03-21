package com.example.telros_test_task.api;

import com.example.telros_test_task.model.MyUser;
import com.example.telros_test_task.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/admin/showFromForUpdate/{id}")
    public String showFromForUpdate(@PathVariable(value = "id") Long id, Model model){
        MyUser user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update_user";
    }

    @GetMapping("/admin")
    public String getHomePage(Model model){
        return findPaginated(1, "FirstName", "asc", model);
    }


    @GetMapping("/admin/page/{numberPage}")
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
        return "admin";
    }

    @PostMapping("/admin/updateUser")
    public String updateUser(@ModelAttribute(value = "user") MyUser myUser){
        userService.saveUser(myUser);
        return "redirect:/admin";
    }

    @GetMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
    @PostMapping("/admin/save")
    public String saveUser(@RequestParam(value = "firstName") String firstName,
                           @RequestParam(value = "lastName") String lastName,
                           @RequestParam(value = "surname") String surname,
                           @RequestParam(value = "birthday") @DateTimeFormat(pattern = "dd-MM-yyyy") Date birthday,
                           @RequestParam(value = "email")String email,
                           @RequestParam(value = "phoneNumber") String phoneNumber){
        MyUser myUser = new MyUser();
        myUser.setFirstName(firstName);
        myUser.setLastName(lastName);
        myUser.setSurname(surname);
        myUser.setBirthday(birthday);
        myUser.setEmail(email);
        myUser.setTelephoneNumber(Integer.parseInt(phoneNumber));
        userService.saveUser(myUser);
        return "redirect:/admin";
    }

}
