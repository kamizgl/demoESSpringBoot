package cn.hadron.demo.service;

import cn.hadron.demo.bean.BookBean;
import cn.hadron.demo.bean.PositionBean;
import cn.hadron.demo.dao.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("positionServiceImpl")
public class PositionServiceImpl implements PositionService {

    @Autowired
    @Qualifier("positionRepository")
    private PositionRepository positionRepository;


    @Override
    public PositionBean save(PositionBean blog) {
        return positionRepository.save(blog);
    }

    @Override
    public void delete(PositionBean blog) {
         positionRepository.delete(blog);
    }

    @Override
    public Optional<PositionBean> findOne(String id) {
        return Optional.empty();
    }

    @Override
    public List<PositionBean> findAll() {
        return null;
    }

    @Override
    public Page<PositionBean> findByName(String name, PageRequest pageRequest) {
        return positionRepository.findByName(name,pageRequest);
    }
}
