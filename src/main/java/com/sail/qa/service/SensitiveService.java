package com.sail.qa.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: sail
 * @Date: 2018/12/24 22:01
 * @Version 1.0
 */
@Service
public interface SensitiveService extends InitializingBean {

    class TrieNode{
        private boolean end = false;//表示是否是结束点

        private Map<Character,TrieNode> subNodes = new HashMap<>();

        public void addSubNode(Character key,TrieNode node){
            subNodes.put(key, node);
        }

        public TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }

        public boolean isEnd() {
            return end;
        }

        public void setEnd(boolean end) {
            this.end = end;
        }
    }


}
