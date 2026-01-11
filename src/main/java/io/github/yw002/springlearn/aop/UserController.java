package io.github.yw002.springlearn.aop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 相当于 @ResponseBody + @Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/getById/{id}")
    @Log(name = "根据ID获取用户的方法")
    public String getById(@PathVariable("id") Long id) {
        return "I am a Test User";
    }
}
