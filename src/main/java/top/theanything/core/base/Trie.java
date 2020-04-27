package top.theanything.core.base;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author zhou
 * @Description  Trie 前缀树 优化HandlerMapping
 * @createTime 2020-04-12
 */
public class Trie {

    private  int count;
    public Trie() {
    }
    static class TrieNode{
        private  final int ALPHABET = 200; //大小写单词 、 数字 字符
        public TrieNode[] node;
        Method method;
        protected TrieNode() {
            node = new TrieNode[ALPHABET];
        }
    }

    private static TrieNode head = new TrieNode(); //保存头结点

    public void insert(String path,Method method){
        char[] chars = path.toCharArray();
        TrieNode temp = head;
        for(int i = 0 ; i < chars.length ; i++){
            if(temp.node[chars[i]] == null){  //没有找到该字符，则在数组该位置上创建一个TrieNode
                temp.node[chars[i]] = new TrieNode();
            }
            temp = temp.node[chars[i]];  //移到刚创建的TrieNode上
            if(i == chars.length-1)  //当到了path末尾将value保存在TrieNode节点的value中
                temp.method = method;
        }
        count++;
    }

    public Method get(String path){
         char[] chars = path.toCharArray();
         Method value = null;
         TrieNode temp = head;
         for(int i = 0 ; i < chars.length ; i ++) {
             if (temp.node[chars[i]] == null)
                 return null;
             temp = temp.node[chars[i]];
             if ( i == chars.length - 1 && temp.method != null) // 当path匹配完的那个node如果有value 则返回
                 value = (Method) temp.method;
         }
         return value;
    }
    public int getCount(){
        return count;
    }

}
