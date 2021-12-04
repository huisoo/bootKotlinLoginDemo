package com.hi.loginDemo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.security.MessageDigest
import javax.servlet.http.HttpSession

@Controller
class HtmlController {

    @Autowired
    lateinit var repository: UserRepository

    @GetMapping("/")
    fun index(model: Model):String{
        return "index"
    }

    @GetMapping("/sign")
    fun htmlForm(model:Model) : String{
        return "sign"
    }

    @GetMapping("/test")
    @ResponseBody
    fun test(): String{
        return "test"
    }

    @GetMapping("/post/{num}")
    fun post(model:Model, @PathVariable num : Int){
       println("num:\t${num}")
    }

    @PostMapping("/sign")
    fun postSign(model:Model,
                 @RequestParam(value="id") userId:String,
                 @RequestParam(value="password") password:String) :String{
        try {
            val cryptoPass =crypto(password);
            val user = repository.save(User(userId, cryptoPass))
            println(user.toString());
        }catch(e:Exception){
            e.printStackTrace();
        }

        return "index"
    }

    @GetMapping("/login")
    fun Login():String{

        return "login"
    }

    @PostMapping("/login")
    fun postLogin(model:Model,
                  session: HttpSession,
                  @RequestParam(value="id") userId:String,
                  @RequestParam(value="password") password:String):String{

        var pageName = ""
        try {
            val cryptoPass =crypto(password);
            val dbUser = repository.findByUserId(userId)
            if(dbUser != null){
                val dbPass = dbUser.password

                if(dbPass.equals(cryptoPass)) {
                    session.setAttribute("userId", dbUser.userId)
                    model.addAttribute("title", "welcome")
                    model.addAttribute("userId", userId)

                    pageName = "welcome"
                }else{
                    model.addAttribute("title", "login")
                    pageName = "login";
                }
            }
        }catch(e:Exception){
            e.printStackTrace();
        }

        return pageName
    }

    fun crypto(ss:String):String{
        val sha = MessageDigest.getInstance("SHA-256")
        val hexa = sha.digest(ss.toByteArray())
        val crypto_str=hexa.fold("", {str, it -> str+"%02x".format(it)})
        return crypto_str
    }
}