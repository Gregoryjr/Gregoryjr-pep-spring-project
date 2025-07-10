package com.example.service;
import com.example.entity.Message; 
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
public MessageRepository messageRepository;
  @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public List<Message> getAllMessages(){
        return messageRepository.findAll();


    }


public Optional <Message> getMessageById(Integer id){
    return messageRepository.findById(id);
}

public List<Message> getAllMessageFromUser(Integer id ){
    return messageRepository.findByPostedBy(id);
}

public boolean updateMessage(Integer id, Message replacement){
        
        if( replacement.getMessageText().length()<1|| replacement.getMessageText().length()>255){
            return false;
       }
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent()){
            Message message = optionalMessage.get();
            message.setMessageText(replacement.getMessageText());
            messageRepository.save(message);
            return true;
        }
        return false;

    }

        public Message persistMessage(Message message){
        return messageRepository.save(message);
    }

       public void deletemessage(Integer id){
        messageRepository.deleteById(id);
    }


}
