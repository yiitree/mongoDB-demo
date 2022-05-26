package com.example.mongodbdemo.dao;

import com.example.mongodbdemo.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment,String>{

    /**
     * 注意，这个方法名格式是固定的
     * findBy：固定格式
     * Parentid：对应的字段名
     * 根据父id，查询子评论的分页列表
     * @param parentid
     * @param pageable 分页条件
     * @return
     */
    Page<Comment> findByParentid(String parentid, Pageable pageable);

}
