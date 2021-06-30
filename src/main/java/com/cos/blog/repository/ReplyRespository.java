package com.cos.blog.repository;

import com.cos.blog.DTO.ReplySaveDto;
import com.cos.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReplyRespository extends JpaRepository<Reply, Integer> {

    //Interface안에선 public 생략가능

    @Modifying
    @Query(value ="INSERT INTO reply(userid, boardId, content, createDate) values(?1,?2,?3,now())",nativeQuery = true)
    int mSave(int userId, int boardId , String content); //업데이트된 행의 개수를 리턴해줌


}
