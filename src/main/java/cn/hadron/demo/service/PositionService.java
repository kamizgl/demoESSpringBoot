package cn.hadron.demo.service;
import cn.hadron.demo.bean.BookBean;
import cn.hadron.demo.bean.PositionBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PositionService {

    PositionBean save(PositionBean blog);

    void delete(PositionBean blog);

    Optional<PositionBean> findOne(String id);

    List<PositionBean> findAll();

    Page<PositionBean> findByName(String name, PageRequest pageRequest);

}
