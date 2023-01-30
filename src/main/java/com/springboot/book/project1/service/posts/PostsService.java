package com.springboot.book.project1.service.posts;

import com.springboot.book.project1.domain.posts.Posts;
import com.springboot.book.project1.domain.posts.PostsRepository;
import com.springboot.book.project1.web.dto.PostsResponseDto;
import com.springboot.book.project1.web.dto.PostsSaveRequestDto;
import com.springboot.book.project1.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    
    @Transactional
    public long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts =
                postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당게시글이 없습니다. id" + id));

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity =
                postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당게시글이 없습니다. id" + id));

        return new PostsResponseDto(entity);
    }
}
