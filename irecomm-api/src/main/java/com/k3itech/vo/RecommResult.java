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
