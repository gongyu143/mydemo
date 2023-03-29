package com.gongyu.mydemo.repository;
import com.gongyu.mydemo.bean.es.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * @author wengwx
 * @date 2023/3/29 
 * @des
 */



public interface UserRepository extends CrudRepository<User,String> {


}
