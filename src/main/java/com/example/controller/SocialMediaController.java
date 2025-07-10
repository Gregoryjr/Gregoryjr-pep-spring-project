package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.MessageService;

import com.example.service.AccountService;
import java.util.List;
import java.util.Optional;
import com.example.entity.Message; 
import com.example.entity.Account; 

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
@Autowired
 private MessageService messageService;
 @Autowired
 private AccountService accountService;

  @GetMapping("/messages")
    public List<Message> getAllMessagesMessagesAvailable() {
        System.out.println("found the endpoint");
       
        return messageService.getAllMessages();
    
    }


    @GetMapping("/messages/{messageId}")
    public @ResponseBody Optional<Message> getmessage (@PathVariable Integer messageId){
 System.out.println("message id endpoint found the endpoint");
  Optional<Message> optionalMessage = messageService.getMessageById(messageId);
 if(optionalMessage.isPresent()){
       return  messageService.getMessageById(messageId);
 }else{
    return null;
}
    } 

@PatchMapping("messages/{messageId}")
public ResponseEntity<Integer> updateM( @PathVariable Integer messageId, @RequestBody Message newData){
    System.out.println("message update endpoint found the endpoint");
      System.out.println(newData.getMessageText());
        System.out.println(messageId);
        
    boolean updated = messageService.updateMessage(messageId, newData);
     if (updated) {
        return ResponseEntity.ok(1);
    } else {
        return ResponseEntity.badRequest().build();
    }
}

    @GetMapping("/accounts/{accountId}/messages")
    public @ResponseBody List<Message> getmessgbyid (@PathVariable Integer accountId){
        
 System.out.println("message id endpoint found the endpoint");
  return messageService.getAllMessageFromUser(accountId);
        
     
 

    } 
    @PostMapping("messages")
    public @ResponseBody  ResponseEntity<Message> makeMessage(@RequestBody Message message){
        System.out.println(" creation endpoint for message");
       Optional<Account> myaccount  = accountService.getAccountById(message.getPostedBy());
    boolean valid = (message.getMessageText().length()>0 && message.getMessageText().length()<256);
    if(valid && myaccount.isPresent()){
        messageService.persistMessage(message);
       return ResponseEntity.ok(messageService.persistMessage(message));
    }
    return  ResponseEntity.badRequest().build();
        
      




    }



        @PostMapping("register")
    public @ResponseBody  ResponseEntity<Account> makeAccount(@RequestBody Account account){
        System.out.println(" creation endpoint for account");
        //accountService.persistAccount(account);
         boolean accountExists = accountService.getAccountByUser(account.getUsername()).isPresent();
        if(!accountExists){
            System.out.println("seen");
        return ResponseEntity.ok(accountService.persistAccount(account));
        }
        else{
            System.out.println(" failure seen");
    return ResponseEntity.status(HttpStatus.CONFLICT).build();  

        }
    

    }

 @PostMapping("login")

 public @ResponseBody ResponseEntity<Account> log(@RequestBody Account account){
    System.out.println(" loggin attempt");
    Optional<Account> newAccount = accountService.login(account.getUsername(), account.getPassword());
    if(newAccount.isPresent()){
    return ResponseEntity.ok(newAccount.get());
    }
    else{
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
 }
    //8080/login

    @DeleteMapping("/messages/{messageId}")

    public ResponseEntity<Integer> deletemess(@PathVariable Integer messageId){

        if(messageService.getMessageById(messageId).isPresent()){
        messageService.deletemessage(messageId);
        return ResponseEntity.ok(1);
        }
        else{
        return ResponseEntity.ok(null);
        }

        

    }

}
