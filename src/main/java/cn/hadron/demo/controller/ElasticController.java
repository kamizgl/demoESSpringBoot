package cn.hadron.demo.controller;

import cn.hadron.demo.bean.BookBean;
import cn.hadron.demo.bean.PositionBean;
import cn.hadron.demo.dao.BookRepository;
import cn.hadron.demo.dao.PositionRepository;
import cn.hadron.demo.service.BookService;
import cn.hadron.demo.service.PositionService;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ElasticController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository  bookRepository;

    @Autowired
    private PositionService positionService;
    @Autowired
    private PositionRepository positionRepository;
    @RequestMapping("/book/{id}")
    @ResponseBody
    public BookBean getBookById(@PathVariable String id){
        Optional<BookBean> opt =bookService.findById(id);
        BookBean book=opt.get();
        System.out.println(book);
        return book;
    }

    @RequestMapping("/save")
    @ResponseBody
    public void Save(){
        BookBean book=new BookBean("1","ES入门教程","程裕强","2018-10-01");
        System.out.println(book);
        bookService.save(book);
    }


    @RequestMapping("/query")
    @ResponseBody
    public void query(){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("title","ES入门教程"));
        Page<BookBean> search = bookRepository.search(nativeSearchQueryBuilder.build());


//        BookBean book=new BookBean("1","ES入门教程","程裕强","2018-10-01");
//        System.out.println(book);
//        bookService.save(book);
        System.out.println(search);
    }

    @RequestMapping("/queryP")
    @ResponseBody
    public void queryp(){

        //        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchAllQuery())
//                .withIndices(INDEX_NAME)
//                .withTypes(TYPE_NAME)
//                .withFields("message")
//                .withPageable(PageRequest.of(0, 10))
//                .build();

        //坐标集合
        List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
        GeoPoint geoPoint = new GeoPoint();
        geoPoint.reset(31.239516, 121.41944);
        geoPoints.add(geoPoint);

        GeoPoint geoPoint2 = new GeoPoint();
        geoPoint2.reset(31.236706, 121.42986);
        geoPoints.add(geoPoint2);

        GeoPoint geoPoint3 = new GeoPoint();
        geoPoint3.reset(31.24566, 121.424111);
        geoPoints.add(geoPoint3);


        //拼接查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        QueryBuilder locationBuilder = QueryBuilders.geoPolygonQuery("location", geoPoints);
        QueryBuilder isdeleteBuilder = QueryBuilders.matchAllQuery();
        boolQueryBuilder.filter(locationBuilder);
        boolQueryBuilder.must(isdeleteBuilder);

        //最終去查詢
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withPageable(PageRequest.of(0,100));
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        Page<PositionBean> search = positionRepository.search(nativeSearchQueryBuilder.build());


        System.out.println(search);
    }



    @RequestMapping("/queryPkm")
    @ResponseBody
    public void querypKm(){

        //拼接条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        QueryBuilder isdeleteBuilder = QueryBuilders.matchAllQuery();
        // 以某点为中心，搜索指定范围
        GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("location");
        distanceQueryBuilder.point(31.240869, 121.458118121);
        //查询单位：km
        distanceQueryBuilder.distance(10, DistanceUnit.KILOMETERS);
        boolQueryBuilder.filter(distanceQueryBuilder);
        boolQueryBuilder.must(isdeleteBuilder);


        //最終去查詢
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withPageable(PageRequest.of(0,100));
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        Page<PositionBean> search = positionRepository.search(nativeSearchQueryBuilder.build());


        System.out.println(search);
    }

    @RequestMapping("/savePosition")
    @ResponseBody
    public void savePosition(){

        PositionBean positionBean=new PositionBean("2","shanghaiHuanqiugang","31.239346,121.424219");

        positionService.save(positionBean);
//        double da=121.423177d;
//        double xiao=31.239423d;
//        for (int i = 0; i <1000 ; i++) {
//            xiao=xiao+ 0.00001 ;
//            da = da + 0.0001d;
//            PositionBean positionBean=new PositionBean(i,"shanghaiHuanqiugang",xiao  + 0.001+"", da + 0.0001d+"");
//            positionService.save(positionBean);
//
//        }
    }
}
