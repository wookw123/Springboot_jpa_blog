package com.cos.blog.repository;

import com.cos.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRespository extends JpaRepository<Reply,Integer> {
}
