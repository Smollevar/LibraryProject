package ru.library.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/first/calculator")
public class Calc {

    @GetMapping()
    public String Calc(@RequestParam (value = "a", required = false) int a,
                       @RequestParam (value = "b", required = false) int b,
                       @RequestParam (value = "action", required = false) String action,
                       Model model) {
        double res = 0;
        System.out.println(action);
        switch (action) {
            case "add" -> res = a + b;
            case "sub" -> res = a - b;
            case "mul" -> res = a * b;
            case "div" -> res = (double) a / b;
        }
        model.addAttribute("result", res);
        return "calc/calculator";
    }
}
