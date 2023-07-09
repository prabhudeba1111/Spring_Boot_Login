package com.example.loginapi.service;

import com.example.loginapi.entity.blog.Comment;
import com.example.loginapi.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    private ArrayList<String> commentString(ArrayList<Comment> Comments){
        ArrayList<String> comments = new ArrayList<>();
        for (Comment comment: Comments){
            comments.add(comment.getContent());
        }
        return comments;
    }
    
    public ArrayList<String> findAllByPost(long id){
        return commentString((ArrayList<Comment>) commentRepository.findAllByPost(id));
    }

    public Comment findById(long id){
        Optional<Comment> opt = commentRepository.findById(id);
        return opt.orElse(null);
    }
}
