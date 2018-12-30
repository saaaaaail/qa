package com.sail.qa.service.impl;

import com.sail.qa.service.SensitiveService;
import org.apache.commons.lang.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: sail
 * @Date: 2018/12/24 22:02
 * @Version 1.0
 */

@Service
public class SensitiveServiceImpl implements SensitiveService  {

    Logger logger = LoggerFactory.getLogger(SensitiveServiceImpl.class);
    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            //构造字典树
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineTxt=null;
            System.out.println("reader: "+reader);
            while ((lineTxt = bufferedReader.readLine())!=null){
                lineTxt = lineTxt.trim();
                addWord(lineTxt);
            }

            reader.close();

        }catch (Exception e) {
            logger.error("读取敏感词文件失败" + e.getMessage());
        }
    }

    //增加关键词
    private void addWord(String lineTxt){
        TrieNode tmpNode = rootNode;
        for (int i=0;i<lineTxt.length();i++){
            Character c = lineTxt.charAt(i);
            TrieNode node = tmpNode.getSubNode(c);

            if (node ==null){
                node = new TrieNode();
                tmpNode.addSubNode(c,node);
            }

            tmpNode = node;

            if (i==lineTxt.length()-1){
                tmpNode.setEnd(true);
            }
        }
    }

    //根节点用key存储第一个节点
    private TrieNode rootNode = new TrieNode();

    private boolean isSymbol(char c){
        int ic = (int)c;
        //东亚文字0x2E80-0x9FFF与英文
        return !CharUtils.isAsciiAlphanumeric(c) && (ic <0x2E80 || ic>0x9FFF);//取反
    }

    public String filter(String text){
        if (StringUtils.isEmpty(text)) {
            return text;
        }

        String replacement = "***";
        StringBuilder result = new StringBuilder();

        TrieNode tmpNode = rootNode;//指向字典树
        int begin = 0;//指向文本
        int position = 0;//指向文本的当前位置

        while (position<text.length()){
            Character c = text.charAt(position);

            if (isSymbol(c)){
                if (tmpNode == rootNode){
                    result.append(c);
                    begin++;
                }
                System.out.println("空格");
                position++;
                continue;
            }

            tmpNode = tmpNode.getSubNode(c);

            if (tmpNode==null){
                result.append(text.charAt(begin));
                position = begin+1;
                begin=position;
                tmpNode = rootNode;
            }else if (tmpNode.isEnd()){
                //发现敏感词
                result.append(replacement);
                position = position+1;
                begin=position;
                tmpNode = rootNode;
            }else {
                position++;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }

    /*public static void main(String[] argv){
        SensitiveServiceImpl sensitiveService = new SensitiveServiceImpl();
        sensitiveService.addWord("色情");
        sensitiveService.addWord("赌博");
        System.out.println(sensitiveService.filter(" 你 好 色 情"));
    }*/
}
