package com.gongyu.mydemo.repository;
import com.github.pagehelper.Page;
import com.gongyu.mydemo.bean.es.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wengwx
 * @date 2023/3/29 
 * @des
 */



public interface UserRepository extends ElasticsearchRepository<User,String> {

    /**
     * 自定义查询 根据姓名
     * @param name
     * @return
     */
    List<User> findStudentBysName(String name);

    /**
     * 模糊查询 contains： 支持 "*xue*" 匹配 containing like  支持xue* 匹配
     * @param name
     * @return
     */
    List<User> findStudentBysNameContaining(String name);

    /**
     * 模糊查询
     * @param name
     * @return
     */
    List<User> findStudentBysNameLike(String name);

    /**
     * 模糊查询
     * @param name
     * @return
     */
    List<User> findStudentBysNameContains(String name);

    /**
     * 分页模糊查询
     * @param name
     * @param pageable
     * @return
     */
    List<User> findStudentBysNameContaining(String name, Pageable pageable);


}
