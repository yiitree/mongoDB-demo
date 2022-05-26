package com.example.mongodbdemo.service;

import com.example.mongodbdemo.dao.CommentRepository;
import com.example.mongodbdemo.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    //注入MongoTemplate
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存一个评论
     */
    public void saveComment(Comment comment) {
        //如果需要自定义主键，可以在这里指定主键；如果不指定主键，MongoDB会自动生成主键
        //设置一些默认初始值。。。
        //调用dao
        commentRepository.save(comment);
    }

    /**
     * 更新评论
     */
    public void updateComment(Comment comment) {
        //调用dao
        commentRepository.save(comment);
    }

    /**
     * 根据id删除评论
     */
    public void deleteCommentById(String id) {
        //调用dao
        commentRepository.deleteById(id);
    }

    /**
     * 查询所有评论
     */
    public List<Comment> findCommentList() {
        //调用dao
        return commentRepository.findAll();
    }

    /**
     * 根据id查询评论
     */
    public Comment findCommentById(String id) {
        //调用dao
        return commentRepository.findById(id).get();
    }

    /**
     * 根据父id查询分页列表
     *
     * @param parentid
     * @param page     当前第几页
     * @param size     一页大小
     * @return
     */
    public Page<Comment> findCommentListPageByParentid(String parentid, int page, int size) {
        return commentRepository.findByParentid(parentid, PageRequest.of(page - 1, size));
    }

    /**
     * 点赞-效率低
     * 查询两次，效率低
     */
    public void updateCommentThumbupToIncrementingOld(String id) {
        Comment comment = commentRepository.findById(id).get();
        comment.setLikenum(comment.getLikenum() + 1);
        commentRepository.save(comment);
    }

    /**
     * 点赞数+1
     * 优化版本，直接执行inc操作，增加1
     */
    public void updateCommentLikenum(String id) {
        // 1. 查询条件
        Query query = Query.query(Criteria.where("_id").is(id));

        // 2. 更新条件
        Update update = new Update();
        // 局部更新，相当于$set
        // update.set(key,value);

        // 递增$inc，如果是1可以省略
        // update.inc("likenum",1);
        update.inc("likenum");

        // 参数1：查询对象
        // 参数2：更新对象
        // 参数3：集合的名字 或 实体类的类型
        // mongoTemplate.updateFirst(query,update,"comment");
        mongoTemplate.updateFirst(query, update, Comment.class);
    }
}
