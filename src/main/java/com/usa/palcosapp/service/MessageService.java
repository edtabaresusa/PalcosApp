package com.usa.palcosapp.service;

import com.usa.palcosapp.model.Message;
import com.usa.palcosapp.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public List<Message> getAll(){ return messageRepository.getAll();}

    public Optional<Message> getById(Integer id){ return messageRepository.getById(id);}

    public Message save(Message message){
        Optional<Message> optional;
        if (message.getIdMessage() == null){
            return messageRepository.save(message);
        } else {
            optional = messageRepository.getById(message.getIdMessage());
            if(optional.isEmpty()){
                return messageRepository.save(message);
            } else {
                return message;
            }
        }
    }

    public  Message update(Message message){
        if (message.getIdMessage()!= null){
            Optional<Message> optional = messageRepository.getById(message.getIdMessage());
            if(!optional.isEmpty()){
                if (message.getMessageText() != null){
                    optional.get().setMessageText(message.getMessageText());
                    optional.get().setClient(message.getClient()); //Esto se hace por la relación muchos a uno del modelo
                    optional.get().setBox(message.getBox()); //Esto se hace por la relación muchos a uno del modelo
                }
                messageRepository.save(optional.get());
                return optional.get();
            } else {
                return message;
            }
        } else {
            return message;
        }
    }

    public boolean delete(Integer id){
        Boolean aBoolean = getById(id).map(message -> {
            messageRepository.delete(message);
            return true;
        }).orElse(false);
        return aBoolean;
    }
}
