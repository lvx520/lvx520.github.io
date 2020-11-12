package com.ssm.lv.lucene;

import com.github.pagehelper.util.StringUtil;
import com.ssm.lv.entity.Course;
import com.ssm.lv.entity.Smalltype;
import com.ssm.lv.utils.DateUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 使用lucene对博客进行增删改查
 * @author lv
 * @date 2020/7/10 - 16:47
 */
public class BlogIndex {
    private Directory dir=null;
    private String lucenePath="luceneDirectry";
    /**
     * 获取对lucene的写入法
     */
    private IndexWriter getWriter() throws Exception{
        dir=FSDirectory.open(Paths.get(lucenePath,new String[0]));

        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
        IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
        IndexWriter writer=new IndexWriter(dir,iwc);
        return writer;
    }
    /**
     * 增加索引
     */
    public void addIndex(Smalltype smalltype)throws Exception{

        IndexWriter writer=getWriter();
        Document doc=new Document();
        doc.add(new StringField("id",String.valueOf(smalltype.getId()), Field.Store.YES));
        doc.add(new TextField("typename",smalltype.getTypename(), Field.Store.YES));
        doc.add(new TextField("blongtype",smalltype.getBlongtype(), Field.Store.YES));
        doc.add(new TextField("summary",smalltype.getSummary(), Field.Store.YES));

        writer.addDocument(doc);
        writer.close();
    }

    /**
     * 更新索引
     *
     */
    public void updateIndex(Smalltype smalltype)throws Exception{
        IndexWriter writer=getWriter();
        Document doc=new Document();
        doc.add(new StringField("id",String.valueOf(smalltype.getId()), Field.Store.YES));
        doc.add(new TextField("typename",smalltype.getTypename(), Field.Store.YES));
        doc.add(new TextField("blongtype",smalltype.getBlongtype(), Field.Store.YES));
        doc.add(new TextField("summary",smalltype.getSummary(), Field.Store.YES));

        writer.updateDocument(new Term("id",String.valueOf(smalltype.getId())),doc);
        writer.close();
    }

    /**
     * 删除索引
     */
    public void deleteIndex(String blogId)throws Exception{
        IndexWriter writer=getWriter();
        writer.deleteDocuments(new Term[]{new Term("id",blogId)});
        writer.forceMergeDeletes();
        writer.commit();
        writer.close();
    }
    /**
     * 搜素索引
     */
    public List<Smalltype> searchBlog(String q)throws Exception{
        List<Smalltype> blogList=new LinkedList<>();
        dir=FSDirectory.open(Paths.get(lucenePath,new String[0]));
        IndexReader reader=DirectoryReader.open(dir);
        IndexSearcher is=new IndexSearcher(reader);
        //放入查询条件
        BooleanQuery.Builder booleanQuery=new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
        QueryParser parser=new QueryParser("typename",analyzer);
        Query query=parser.parse(q);
        QueryParser parser2=new QueryParser("summary",analyzer);
        Query query2=parser2.parse(q);
        QueryParser parser3=new QueryParser("blongtype",analyzer);
        Query query3=parser3.parse(q);
        booleanQuery.add(query,BooleanClause.Occur.SHOULD);
        booleanQuery.add(query2,BooleanClause.Occur.SHOULD);
        booleanQuery.add(query3,BooleanClause.Occur.SHOULD);
        //最大返回100条
        TopDocs hits=is.search(booleanQuery.build(),100);
        //高亮搜索字
        QueryScorer scorer=new QueryScorer(query);
        Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter,scorer);
        highlighter.setTextFragmenter(fragmenter);
        //遍历查询结果，放入blogList
        for(ScoreDoc scoreDoc:hits.scoreDocs){
            Document doc=is.doc(scoreDoc.doc);
            Smalltype smalltype=new Smalltype();
            smalltype.setId(Integer.parseInt(doc.get("id")));
            smalltype.setTypename(doc.get("typename"));
            smalltype.setBlongtype(doc.get("blongtype"));
            smalltype.setSummary(doc.get("summary"));
            smalltype.setImgurl(doc.get("imgurl"));
            String typename=doc.get("typename");
            String blongtype=doc.get("blongtype");
            String summary=StringEscapeUtils.escapeHtml(doc.get("summary"));
            if(typename!=null){
                TokenStream tokenStream=analyzer.tokenStream("typename",new StringReader(typename));
                String hTitle=highlighter.getBestFragment(tokenStream,typename);
                if(StringUtil.isEmpty(hTitle)){
                    smalltype.setTypename(typename);
                }else{
                    smalltype.setTypename(hTitle);
                }
            }
            if(blongtype!=null){
                TokenStream tokenStream=analyzer.tokenStream("blongtype",new StringReader(blongtype));
                String hTitle=highlighter.getBestFragment(tokenStream,blongtype);
                if(StringUtil.isEmpty(hTitle)){
                    smalltype.setBlongtype(blongtype);
                }else{
                    smalltype.setBlongtype(hTitle);
                }
            }
            if(summary!=null){
                TokenStream tokenStream=analyzer.tokenStream("summary",new StringReader(summary));
                String hContent=highlighter.getBestFragment(tokenStream,summary);
                if(StringUtil.isEmpty(hContent)){
                    if(summary.length()<=50){
                        smalltype.setSummary(summary);
                    }else{
                        smalltype.setSummary(summary.substring(0,50));
                    }

                }else{
                    smalltype.setSummary(hContent);
                }
            }
         blogList.add(smalltype);
      }
        return blogList;
    }
}
