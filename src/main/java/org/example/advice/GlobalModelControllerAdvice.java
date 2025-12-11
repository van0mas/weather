package org.example.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelControllerAdvice {

    @ModelAttribute
    public void addUsernameToModel(HttpServletRequest request, Model model) {

        User user = (User) request.getAttribute("user");
        String username = null;

        if (user != null) {
           username = user.getUsername();
        }

        model.addAttribute("username", username);
    }
}
