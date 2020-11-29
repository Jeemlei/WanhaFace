package wanhaface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

@Controller
@SpringBootApplication
public class WanhaFace {

    @RequestMapping("/")
    @ResponseBody
    String home() {
      return "Hello Wepa!";
    }

    public static void main(String[] args) {
        SpringApplication.run(WanhaFace.class, args);
    }
}