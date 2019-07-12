package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * FileName: SearchServiceImpl.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/11 18:51
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    /**
     * 添加到索引库
     * @param goods
     * @return
     */
    @Override
    public int addGoods(Goods goods) {

        // 把内容存到SolrInputDocument对象中
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", goods.getId());
        document.setField("gname", goods.getGname());
        document.setField("ginfo", goods.getGinfo());
        document.setField("gimage", goods.getGimage());
        document.setField("gprice", goods.getGprice().floatValue());
        document.setField("gsave", goods.getGsave());

        try {
            // 添加到索引库
            solrClient.add(document);
            // 提交
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据关键字查询结果
     * @param keyword
     * @return
     */
    @Override
    public List<Goods> searchByKey(String keyword) {
        SolrQuery solrQuery = null;
        if (keyword == null || keyword.trim().equals("")) {
            solrQuery = new SolrQuery("*:*");
        } else {
//            solrQuery = new SolrQuery("gname:"+keyword+" || ginfo:"+keyword);
            String str = String.format("gname:%s || ginfo:%s", keyword, keyword);
            solrQuery = new SolrQuery(str);
        }

        // 设置查询关键字的高亮
        solrQuery.setHighlight(true); // 开启高亮
        solrQuery.setHighlightSimplePre("<font color='red'>"); // 设置关键字的前缀
        solrQuery.setHighlightSimplePost("</font>"); // 设置关键字的后缀
        solrQuery.addHighlightField("gname"); // 设置高亮的字段

        List<Goods> goodsList = new ArrayList<>();
        try {
            // 查询获得响应对象
            QueryResponse queryResponse = solrClient.query(solrQuery);

            // 获得查询结果
            SolrDocumentList results = queryResponse.getResults();

            // 获得高亮的关键字
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

            for (SolrDocument document : results) {
                Goods goods = new Goods();

                // 得到每个字段
                int id = Integer.parseInt(document.getFieldValue("id") + "");
                String gname = document.getFieldValue("gname")+"";
                String gimage = document.getFieldValue("gimage")+"";
                BigDecimal gprice = new BigDecimal(document.getFieldValue("gprice")+"");
                int gsave = (int)document.getFieldValue("gsave");

                // 给对象赋值
                goods.setId(id);
                goods.setGname(gname);
                goods.setGimage(gimage);
                goods.setGprice(gprice);
                goods.setGsave(gsave);

                // 处理高亮的结果
                if (highlighting.containsKey(id+"")) {
                    Map<String, List<String>> stringListMap = highlighting.get(id + "");
                    if (stringListMap.containsKey("gname")) {
                        String highlight = stringListMap.get("gname").get(0);
                        goods.setGname(highlight);
                    }
                }
                goodsList.add(goods);
            }
            
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goodsList;
    }
}
