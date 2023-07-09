package com.example.loginapi.service;

import com.example.loginapi.entity.blog.Post;
import com.example.loginapi.payload.request.PostRequest;
import com.example.loginapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public ArrayList<Post> findAllPosts(){
        return (ArrayList<Post>) postRepository.findAll();
    }

    public ArrayList<Post> findAllByEmail(String email){
        return (ArrayList<Post>) postRepository.findAllByEmail(email);
    }

    public Post findById(long id){
        Optional<Post> opt = postRepository.findById(id);
        if (opt.isPresent()){
            return opt.get();
        }
        else{
            return null;
        }
    }
    
    public ArrayList<PostResponse> convertToReturn(ArrayList<Post> Posts){
        ArrayList<PostResponse> posts = new ArrayList<>();
        for(Post post: Posts){
            posts.add(postResponse(post));
        }
        return posts;
    }

    public PostResponse postResponse(Post post){
        long id = post.getId();
        ArrayList<String> comments = commentService.findAllByPost(id);
        PostResponse response = PostResponse.builder()
                .username(userVerify.usernameFromEmail(post.getEmail()))
                .title(post.getTitle())
                .content(post.getContent())
                .created(post.getCreated())
                .comments(comments).build();
        return response;
    }
}
