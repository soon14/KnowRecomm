package com.k3itech.vo;

import com.k3itech.irecomm.re.entity.IreKnowledgeInfo;
import lombok.Data;

/**
 * @author:dell
 * @since: 2021-05-25
 */
@Data
public class RecommResult implements Comparable<RecommResult>{

    private IreKnowledgeInfo info;
    /**
     * 结果匹配命中方式，（"标签，相似度，部门"）
     */
    private String type;
    /**
     * 命中的标签
     */
   private String tags;
    private Double score;

//    private Boolean isPost;


    @Override
    public int compareTo(RecommResult o) {
       Double a =this.score-o.getScore();
       if (a>0){
           return -1;
       }else if (a<0){
           return 1;
       }else {
           return 0;
       }
    }
}
