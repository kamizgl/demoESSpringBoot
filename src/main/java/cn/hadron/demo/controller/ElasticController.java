package cn.hadron.demo.controller;

import cn.hadron.demo.bean.BookBean;
import cn.hadron.demo.dao.BookRepository;
import cn.hadron.demo.service.BookService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
public class ElasticController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository  bookRepository;
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


}
