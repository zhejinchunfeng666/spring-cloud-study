package com.zf.study.mem.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/elastic")
@Slf4j
public class IndexController {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 索引文档
     * @return
     * {
     *     "shardInfo": {
     *         "total": 2,
     *         "successful": 1,
     *         "failures": [],
     *         "failed": 0,
     *         "fragment": false
     *     },
     *     "shardId": {
     *         "index": {
     *             "name": "zf",
     *             "uuid": "_na_",
     *             "fragment": false
     *         },
     *         "id": -1,
     *         "indexName": "zf",
     *         "fragment": true
     *     },
     *     "id": "1",
     *     "type": "_doc",
     *     "version": 5,
     *     "seqNo": 10,
     *     "primaryTerm": 6,
     *     "result": "UPDATED",
     *     "index": "zf",
     *     "fragment": false
     * }
     * @throws IOException
     */
    @PostMapping("/index")
    public Object indexInfo() throws IOException {
        /**
         * {
         *     "first_name" : "amy",
         *     "last_name" :  "ff",
         *     "age" :        32,
         *     "about" :      "I love to go rock climbing",
         *     "interests": [ "sports", "music" ]
         * }
         */
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("first_name","zf");
            builder.field("last_name","amy");
            builder.field("age","33");
            builder.field("about","zf love amy");
            builder.field("interests","["+ "sports"+","+ "music"+"]");

        }
        builder.endObject();
        IndexRequest request = new IndexRequest("zf").id("1").source(builder);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED){
            // handler the document create first time
        }else if(indexResponse.getResult() == DocWriteResponse.Result.UPDATED){
            // handler the document was rewritten
        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()){
            // handler  number of successful shards is less than total shards
        }
        if(shardInfo.getFailed() > 0){
            // handler failure index
        }
        return  indexResponse;
    }

    /**
     *
     * @return
     * {
     *     "fields": {},
     *     "id": "1",
     *     "type": "_doc",
     *     "index": "zf",
     *     "source": {
     *         "about": "zf love amy",
     *         "last_name": "amy",
     *         "interests": "[sports,music]",
     *         "first_name": "zf",
     *         "age": "33"
     *     },
     *     "seqNo": 10,
     *     "exists": true,
     *     "sourceEmpty": false,
     *     "sourceAsBytes": "eyJmaXJzdF9uYW1lIjoiemYiLCJsYXN0X25hbWUiOiJhbXkiLCJhZ2UiOiIzMyIsImFib3V0IjoiemYgbG92ZSBhbXkiLCJpbnRlcmVzdHMiOiJbc3BvcnRzLG11c2ljXSJ9",
     *     "sourceAsMap": {
     *         "about": "zf love amy",
     *         "last_name": "amy",
     *         "interests": "[sports,music]",
     *         "first_name": "zf",
     *         "age": "33"
     *     },
     *     "primaryTerm": 6,
     *     "version": 5,
     *     "sourceAsString": "{\"first_name\":\"zf\",\"last_name\":\"amy\",\"age\":\"33\",\"about\":\"zf love amy\",\"interests\":\"[sports,music]\"}",
     *     "sourceInternal": {
     *         "fragment": true
     *     },
     *     "sourceAsBytesRef": {
     *         "fragment": true
     *     },
     *     "fragment": false
     * }
     * @throws IOException
     */
    @GetMapping("/getIndex")
    public Object getIndexInfo() throws IOException {
        GetRequest request = new GetRequest("zf","1");
        GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
        Map<String, Object> source = getResponse.getSource();
        log.info("source:{}",source);
        return getResponse;
    }

    /**
     * 判断索引是否存在
     * @return
     */
    @GetMapping("/existIndex")
    public Boolean indexExist() throws IOException {
        GetRequest request = new GetRequest("zf","1");
        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        return exists;
    }

    /**
     *
     * @return
     * {
     *     "shardInfo": {
     *         "total": 2,
     *         "successful": 1,
     *         "failures": [],
     *         "failed": 0,
     *         "fragment": false
     *     },
     *     "shardId": {
     *         "index": {
     *             "name": "zf",
     *             "uuid": "_na_",
     *             "fragment": false
     *         },
     *         "id": -1,
     *         "indexName": "zf",
     *         "fragment": true
     *     },
     *     "id": "1",
     *     "type": "_doc",
     *     "version": 6,
     *     "seqNo": 11,
     *     "primaryTerm": 8,
     *     "result": "DELETED",
     *     "index": "zf",
     *     "fragment": false
     * }
     * @throws IOException
     */
    @RequestMapping("/deleteIndex")
    public Object deleteIndex() throws IOException {
        DeleteRequest request = new DeleteRequest("zf","1");
        DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
        return  deleteResponse;
    }

//    public Object updateIndex(){
//        UpdateRequest request = new UpdateRequest("zf","1");
//        Xcon
//    }
}
