package com.ssm.lv.lucene;

import com.github.pagehelper.util.StringUtil;
import com.ssm.lv.entity.Note;
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
public class NoteIndex {
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
    public void addIndex(Note note)throws Exception{

        IndexWriter writer=getWriter();
        Document doc=new Document();
        doc.add(new StringField("id",String.valueOf(note.getId()), Field.Store.YES));
        doc.add(new TextField("title",note.getTitle(), Field.Store.YES));
        doc.add(new TextField("content",note.getContent(), Field.Store.YES));
        doc.add(new TextField("summary",note.getSummary(), Field.Store.YES));
        doc.add(new TextField("releasedate",DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm"), Field.Store.YES));
        doc.add(new TextField("click",String.valueOf(note.getClick()), Field.Store.YES));
        writer.addDocument(doc);
        writer.close();
    }
    /**
     * 更新索引
     *
     */
    public void updateIndex(Note note)throws Exception{
        IndexWriter writer=getWriter();
        Document doc=new Document();
        doc.add(new StringField("id",String.valueOf(note.getId()), Field.Store.YES));
        doc.add(new TextField("title",note.getTitle(), Field.Store.YES));
        doc.add(new TextField("content",note.getContent(), Field.Store.YES));
        doc.add(new TextField("summary",note.getSummary(), Field.Store.YES));
        doc.add(new TextField("click",String.valueOf(note.getClick()), Field.Store.YES));
        writer.updateDocument(new Term("id",String.valueOf(note.getId())),doc);
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
    public List<Note> searchBlog(String q)throws Exception{
        List<Note> blogList=new LinkedList<>();
        dir=FSDirectory.open(Paths.get(lucenePath,new String[0]));
        IndexReader reader=DirectoryReader.open(dir);
        IndexSearcher is=new IndexSearcher(reader);
        //放入查询条件
        BooleanQuery.Builder booleanQuery=new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
        QueryParser parser=new QueryParser("title",analyzer);
        Query query=parser.parse(q);
        QueryParser parser2=new QueryParser("summary",analyzer);
        Query query2=parser2.parse(q);
        QueryParser parser3=new QueryParser("content",analyzer);
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
            Note  note=new Note();
            note.setId(Integer.parseInt(doc.get("id")));
            note.setTitle(doc.get("title"));
            note.setContent(doc.get("content"));
            note.setSummary(doc.get("summary"));
            String title=doc.get("title");
            String content=StringEscapeUtils.escapeHtml(doc.get("content"));
            if(title!=null){
                TokenStream tokenStream=analyzer.tokenStream("title",new StringReader(title));
                String hTitle=highlighter.getBestFragment(tokenStream,title);
                if(StringUtil.isEmpty(hTitle)){
                    note.setTitle(title);
                }else{
                    note.setTitle(hTitle);
                }
            }
            if(content!=null){
                TokenStream tokenStream=analyzer.tokenStream("content",new StringReader(content));
                String hContent=highlighter.getBestFragment(tokenStream,content);
                if(StringUtil.isEmpty(hContent)){
                    if(content.length()<=50){
                        note.setContent(content);
                    }else{
                        note.setContent(content.substring(0,50));
                    }

                }else{
                    note.setContent(hContent);
                }
            }
         blogList.add(note);
      }
        return blogList;
    }
}
