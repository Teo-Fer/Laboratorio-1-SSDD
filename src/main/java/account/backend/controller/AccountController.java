package account.backend.controller;

import account.backend.model.Account;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @RequestMapping(path = "/account", produces = "application/json", params = {"id"}, method = RequestMethod.GET)
    public Account getAccount(@RequestParam("id") int id) {
        Account account = new Account();
        account.setId(id);
        account.setSaldo(2000);
        return account;
    }
}
