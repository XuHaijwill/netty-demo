package org.example.netty.coment.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类说明：
 * http://localhost:8087/coment/index
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String hello(){
        return "index";
    }

}
