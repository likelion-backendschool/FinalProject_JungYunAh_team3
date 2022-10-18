package com.example.app.service;

import com.example.app.domain.PostKeyword;
import com.example.app.repository.PostKeywordRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostKeywordService {

  private final PostKeywordRepository postKeywordRepository;

  /*
  postKeyword 등록
  - 중복 체크
  - String -> Json
   */
  public List<PostKeyword> create(String hashTags) {
    List<PostKeyword> hashTagIdList = new ArrayList<>();

    JSONArray jsonArr = new JSONArray(hashTags);

    for (int i = 0; i < jsonArr.length(); i++) {
      JSONObject jsonObj = jsonArr.getJSONObject(i);
      String content = String.valueOf(jsonObj.get("value"));
      if (postKeywordRepository.findByContent(content).isEmpty()) {
        PostKeyword postKeyword = PostKeyword.builder()
            .content(content)
            .build();
        PostKeyword savedPostKeyword = postKeywordRepository.save(postKeyword);
        hashTagIdList.add(savedPostKeyword);
      }
    }
    return hashTagIdList;
  }
}

