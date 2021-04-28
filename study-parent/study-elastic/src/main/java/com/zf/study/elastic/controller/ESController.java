package com.zf.study.elastic.controller;

import cn.hutool.json.JSONUtil;
import com.zf.study.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/es")
@Slf4j
public class ESController {

    private static  final String INDEX_TEST = "index_test";


    @Autowired
    private RestHighLevelClient client;

    @PutMapping("/index")
    public Object index() throws IOException {
        CreateIndexRequest request  = new CreateIndexRequest(INDEX_TEST);
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        return createIndexResponse;
    }

    @PutMapping("/add")
    public Object addData(@RequestBody User user) throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX_TEST);
        indexRequest.id(user.getId().toString());
        indexRequest.source(JSONUtil.toJsonStr(user), XContentType.JSON);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        log.info("response:{}",response.status());
      return response.status();
    }

    @GetMapping("/getData")
    public Object getData(@RequestParam Integer id) throws IOException {
        GetRequest getRequest = new GetRequest(INDEX_TEST,id.toString());
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        log.info("response:{}",getResponse.getSource());
//        client.close();
        return  getResponse.getSource();

    }

    @GetMapping("/exist")
    public Object exists(@RequestParam Integer id) throws IOException {
        GetRequest getRequest = new GetRequest(INDEX_TEST,id.toString());
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        if (exists){
            return "文档存在";
        }else{
            return "文档不存在";
        }
    }

    @PutMapping("/update")
    public Object update(@RequestBody User user) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(INDEX_TEST,user.getId().toString());
        updateRequest.doc(JSONUtil.toJsonStr(user),XContentType.JSON);
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        log.info("更新返回:{}",updateResponse.getResult());
        return updateResponse.getResult();
    }

    @DeleteMapping("/delete")
    public Object delete(@RequestParam Integer id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX_TEST,id.toString());
        DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
        log.info("删除返回:{}",delete.getResult());
        return delete.getResult();
    }

    @GetMapping("/query")
    public Object query() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("userName","zzff4"))
                .from(0)
                .size(2)
                .sort("id", SortOrder.ASC)
                .explain(true);
        searchRequest.source(sourceBuilder);
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hitsArray = hits.getHits();

        for (SearchHit searchHit : hitsArray) {
            System.out.println(searchHit.getSourceAsString());
        }
        return "hits";
    }

    @GetMapping("/queryAll")
    public Object queryAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery()).from(0).size(10);
//        searchRequest.indices("zf*");
        searchRequest.source(sourceBuilder);
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hitsArray = hits.getHits();
        for (SearchHit searchHit : hitsArray) {
            log.info("返回结果:{}",searchHit.getSourceAsString());
        }
//        client.close();
        return "s";
    }
}
