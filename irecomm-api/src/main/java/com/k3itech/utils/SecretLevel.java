package com.k3itech.utils;

import java.util.Arrays;
import java.util.List;

public class SecretLevel {

    public static List<String> getKnowledgeSecretLevelsByUserLevel(String userLevel) {
        Integer level=Integer.valueOf(userLevel);
        String levels="";
        if (level<=30){
            levels= "非密";
        }else if (level<=60){
            levels= "非密,秘密";
        }else{
            levels= "非密,秘密,机密";
        }

        return Arrays.asList(levels.split(","));
    }

    public static Integer getSecretLevelsByUserLevel(String userLevel) {
        Integer level=Integer.valueOf(userLevel);
        if (level<=30){
            return 1;
        }else if (level<=60){
            return 2;
        }else{
            return 3;
        }
    }

}
