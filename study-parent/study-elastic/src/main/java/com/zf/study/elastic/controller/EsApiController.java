package com.zf.study.elastic.controller;

import cn.hutool.json.JSONUtil;
import com.zf.study.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ES操作API
 * @author zf
 */
@RestController
@RequestMapping("/ES")
@Slf4j
public class EsApiController {

    private static  final  String INDEX_TEST = "test";

    @Autowired
    private RestHighLevelClient client;


    @PostMapping("/addByJson")
    public Object addIndexJson(@RequestBody User user) throws IOException {
        // 索引
        IndexRequest indexRequest = new IndexRequest(INDEX_TEST);
        // 请求文档的ID
        indexRequest.id(user.getId().toString());
        indexRequest.source(JSONUtil.toJsonStr(user), XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        log.info("返回信息:{}",indexResponse);
        return indexResponse.status();
    }

    @PostMapping("/addByMap")
    public Object addIndexMap(@RequestParam Map<String,Object> parasMap) throws IOException {
        // 索引
        IndexRequest indexRequest = new IndexRequest(INDEX_TEST);
        indexRequest.id(parasMap.get("id").toString())
                .source(parasMap);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        log.info("返回信息:{}",indexResponse);
        return indexResponse.status();
    }

    @PostMapping("/addByXContent")
    public Object addIndexXml(@RequestBody User user) throws IOException {

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("id",user.getId());
            builder.field("userName",user.getUserName());
            builder.field("passWord",user.getPassWord());
        }
        builder.endObject();
        // 索引
        IndexRequest indexRequest = new IndexRequest(INDEX_TEST);
        indexRequest.id(user.getId().toString())
                .source(builder);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        log.info("返回信息:{}",indexResponse);
        return indexResponse.status();
    }

    @PostMapping("/addByObj")
    public Object addByObj(@RequestBody User user) throws IOException {
        // 索引
        IndexRequest indexRequest = new IndexRequest(INDEX_TEST);
        indexRequest.id(user.getId().toString())
                .source("id",user.getId(),"userName",user.getUserName(),"passWord",user.getPassWord());
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        log.info("返回信息:{}",indexResponse);
        return indexResponse.status();
    }

    /**
     * 获取文档信息
     * @param id
     * @return
     */
    @GetMapping("/getInfo")
    public Object getInfo(@RequestParam Integer id) throws IOException {
        GetRequest getRequest = new GetRequest(INDEX_TEST,id.toString());
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        log.info("返回信息:{}",getResponse);
        return getResponse.getSource();
    }


    @GetMapping("/exists")
    public Object existsInfo(@RequestParam Integer id) throws IOException {
        GetRequest getRequest = new GetRequest(INDEX_TEST,id.toString());
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        if (exists){
            return  "文档已存在";
        }else {
            return "文档不存在";
        }
    }

    @PutMapping("/updateByJson")
    public Object updateByJson(@RequestBody User user) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(INDEX_TEST,user.getId().toString());
        updateRequest.doc(JSONUtil.toJsonStr(user),XContentType.JSON);
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        log.info("返回信息:{}",updateResponse);
        return updateResponse.status();
    }



    @DeleteMapping("/delete")
    public Object delete(@RequestParam Integer id) throws IOException {
        DeleteRequest request = new DeleteRequest(INDEX_TEST,id.toString());
        DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
        log.info("返回信息:{}",deleteResponse);
        return deleteResponse.status();
    }

    @RequestMapping("/bulk")
    public Object bulk(@RequestBody List<User> userList) throws IOException {
        BulkRequest bulkRequest = new BulkRequest(INDEX_TEST);
        userList.stream().forEach(user -> {
            bulkRequest.add(new IndexRequest().id(user.getId().toString()).source(JSONUtil.toJsonStr(user),XContentType.JSON));
        });
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        log.info("返回信息:{}",bulkResponse);
        for (BulkItemResponse bulkItemResponse:bulkResponse) {
            if (bulkItemResponse.isFailed()){
                BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                return failure.getMessage();
            }
        }
        return bulkResponse.status();
    }

    @GetMapping("/multiGet")
    public Object multiGet(@RequestBody  List<Integer> ids) throws IOException {
        log.info("c参数:{}",ids);
        MultiGetRequest request = new MultiGetRequest();
        ids.stream().forEach(id ->{
            request.add(new MultiGetRequest.Item(INDEX_TEST,id.toString()));
        });
        List<Map<String,Object>> mapList = new ArrayList<>();
        MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
        for (MultiGetItemResponse multiGetItemResponse:response) {
            GetResponse getResponse = multiGetItemResponse.getResponse();
            if (getResponse.isExists()){
                Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
                mapList.add(sourceAsMap);
                log.info("返回信息:{}",sourceAsMap);
            }

        }
        return mapList;
    }

    @GetMapping("/query")
    public Object query() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("userName","zf"))  //精准匹配
                .from(0)  // 分页
                .size(2)
                .sort("id", SortOrder.DESC)
                .explain(true); //按查询匹配度排序
        searchRequest.indices(INDEX_TEST);
        searchRequest.source(sourceBuilder);
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        log.info("返回信息:{}",hits1);
        List<User> userList = new ArrayList<>();
        if (hits1.length > 0){
            for (SearchHit searchHit:hits1) {
                userList.add(JSONUtil.toBean(searchHit.getSourceAsString(),User.class));
            }
        }
        return userList;
    }

    @GetMapping("/queryAll")
    public Object queryAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery())
                .from(0)
                .size(100)
                .sort("id",SortOrder.ASC);
        searchRequest.indices(INDEX_TEST);
        searchRequest.source(sourceBuilder);
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        log.info("返回信息:{}",hits1);
        List<User> userList = new ArrayList<>();
        if (hits1.length > 0){
            for (SearchHit searchHit:hits1) {
                userList.add(JSONUtil.toBean(searchHit.getSourceAsString(),User.class));
            }
        }
        return userList;
    }
}
