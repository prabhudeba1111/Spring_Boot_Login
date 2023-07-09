package com.example.loginapi.controller;

import com.example.loginapi.entity.blog.Comment;
import com.example.loginapi.entity.blog.Post;
import com.example.loginapi.payload.request.PostRequest;
import com.example.loginapi.payload.response.MessageResponse;
import com.example.loginapi.payload.response.PostResponse;
import com.example.loginapi.repository.PostRepository;
import com.example.loginapi.service.CommentService;
import com.example.loginapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    PostRepository postRepository;

    @RequestMapping("")
    public ResponseEntity<?> newPost(@RequestBody PostRequest request, Principal principal){
        Post newpost = new Post(principal.getName(), request.getTitle(), request.getContent());
        postRepository.save(newpost);
        return ResponseEntity.ok(new MessageResponse("New post posted."));
    }

    @RequestMapping("/me")
    public ArrayList<PostResponse> showAllById(Principal principal){
        ArrayList<Post> posts = postService.findAllByEmail(principal.getName());
        ArrayList<PostResponse> responses = postService.convertToReturn(posts);
        return responses;
    }
    
    @RequestMapping("/{id}")
    public ResponseEntity<PostResponse> showById(@PathVariable long id){
        Post post = postService.findById(id);  //comment add karna
        PostResponse response = postService.postResponse(post);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping("/edit/{id}")
    public ResponseEntity<?> editPost(@RequestBody PostRequest request, @PathVariable long id, Principal principal){
        Post post = postService.findById(id);
        if (post==null){
            throw new IllegalArgumentException("Post not found");
        }
        if (!post.getEmail().equals(principal.getName())){
            throw new BadCredentialsException("No access");
        }
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        postRepository.save(post);
        return ResponseEntity.ok(new MessageResponse("Post edited"));
    }

    @RequestMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable long id, Principal principal){
        Post post = postService.findById(id);
        if (post==null){
            throw new IllegalArgumentException("Post not found");
        }
        if (!post.getEmail().equals(principal.getName())){
            throw new BadCredentialsException("No access");
        }
        postRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Post deleted"));
    }
}
