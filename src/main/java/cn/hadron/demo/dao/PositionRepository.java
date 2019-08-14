package cn.hadron.demo.dao;
import cn.hadron.demo.bean.BookBean;
import cn.hadron.demo.bean.PositionBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 接口关系：
 * ElasticsearchRepository --> ElasticsearchCrudRepository --> PagingAndSortingRepository --> CrudRepository
 */
public interface PositionRepository extends ElasticsearchRepository<PositionBean, String> {

    //Optional<BookBean> findById(String id);

    Page<PositionBean> findByName(String name, Pageable pageable);


}
