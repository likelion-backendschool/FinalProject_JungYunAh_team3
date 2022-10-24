package com.example.app.service;

import com.example.app.domain.Post;
import com.example.app.domain.PostHashTag;
import com.example.app.domain.PostKeyword;
import com.example.app.repository.PostHashTagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostHashTagService {

  private final PostHashTagRepository postHashTagRepository;
  private final PostKeywordService postKeywordService;

  /*
  postHashTag 등록
   */
  public void create(Post post, String hashTags) {
    List<PostKeyword> postKeywordList = postKeywordService.create(hashTags);

    for (PostKeyword postKeyword : postKeywordList) {
      PostHashTag postHashTag = PostHashTag.builder()
          .memberId(post.getAuthor().getId())
          .post(post)
          .keyword(postKeyword)
          .build();
      postHashTagRepository.save(postHashTag);
    }
  }
}
