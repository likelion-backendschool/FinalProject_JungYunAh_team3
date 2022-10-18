package com.example.app.service;

import com.example.app.domain.Member;
import com.example.app.domain.Post;
import com.example.app.dto.request.PostDto;
import com.example.app.exception.ErrorType;
import com.example.app.exception.PostException;
import com.example.app.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final PostHashTagService postHashTagService;

  /*
  글 등록
  - 제목, 내용 체크
   */
  public Post write(PostDto postDto, Member member) {
    if (postDto.getSubject().length() == 0 || postDto.getContent().length() == 0) {
      throw new PostException(ErrorType.INSUFFICIENT);
    }
    Post post = Post.builder()
        .author(member)
        .subject(postDto.getSubject())
        .content(postDto.getContent())
        .contentHtml(postDto.getContentHtml())
        .build();

    Post savedPost = postRepository.save(post);
    postHashTagService.create(savedPost, postDto.getHashTags());
    return savedPost;
  }
}
