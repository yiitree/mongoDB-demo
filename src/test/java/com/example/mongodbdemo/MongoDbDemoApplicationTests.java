package com.example.mongodbdemo;

import com.example.mongodbdemo.domain.Comment;
import com.example.mongodbdemo.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class MongoDbDemoApplicationTests {

    //注入Service
    @Autowired
    private CommentService commentService;

    /**
     * 保存一个评论
     */
    @Test
    public void testSaveComment() {
        Comment comment = new Comment();
        comment.setArticleid("100000");
        comment.setContent("测试添加的数据");
        comment.setCreatedatetime(LocalDateTime.now());
        comment.setUserid("1003");
        comment.setNickname("凯撒大帝");
        comment.setState("1");
        comment.setLikenum(0);
        comment.setReplynum(0);
        commentService.saveComment(comment);

    }

    /**
     * 查询所有数据
     */
    @Test
    public void testFindAll() {
        List<Comment> list = commentService.findCommentList();
        System.out.println(list);
    }

    /**
     * 测试根据id查询
     */
    @Test
    public void testFindCommentById() {
        Comment comment = commentService.findCommentById("628f57f4e927cc09c577ccc0");
        System.out.println(comment);
    }

    /**
     * 测试根据父id查询子评论的分页列表
     */
    @Test
    public void testFindCommentListPageByParentid() {
        Page<Comment> pageResponse = commentService.findCommentListPageByParentid("3", 1, 2);
        System.out.println("----总记录数：" + pageResponse.getTotalElements());
        System.out.println("----当前页数据：" + pageResponse.getContent());
    }

    /**
     * 点赞数+1
     * update命令
     */
    @Test
    public void testUpdateCommentLikenum() {
        //对3号文档的点赞数+1
        commentService.updateCommentLikenum("628f57f4e927cc09c577ccc0");
    }

}
