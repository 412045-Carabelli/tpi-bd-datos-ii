package org.datosii.tpidatosii.service;

import org.datosii.tpidatosii.dto.CommentCreateDto;
import org.datosii.tpidatosii.model.Comment;
import org.datosii.tpidatosii.model.Post;
import org.datosii.tpidatosii.model.User;
import org.datosii.tpidatosii.repository.CommentRepository;
import org.datosii.tpidatosii.repository.PostRepository;
import org.datosii.tpidatosii.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public Comment createComment(CommentCreateDto commentDto) {
        Optional<User> author = userRepository.findById(commentDto.getAuthorId());
        Optional<Post> post = postRepository.findById(commentDto.getPostId());

        if (!author.isPresent()) {
            throw new RuntimeException("User not found");
        }
        if (!post.isPresent()) {
            throw new RuntimeException("Post not found");
        }

        Comment comment = new Comment(commentDto.getContent(), author.get(), post.get());

        // Update post comments count
        Post postEntity = post.get();
        postEntity.setCommentsCount(postEntity.getCommentsCount() + 1);
        postRepository.save(postEntity);

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}