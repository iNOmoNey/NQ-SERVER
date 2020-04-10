package top.theanything.base;

/**
 * @author zhou
 * @Description  Trie 前缀树 优化HandlerMapping
 * @createTime 2020-04-08
 */
public class Trie<T> {
    private static TrieNode head = new TrieNode(); //保存一个空的头结点

    static class TrieNode{
        private static final int ALPHABET = 200; //大小写单词 、 数字 字符
        private TrieNode node[];
        private Object value;
        protected TrieNode() {
            this.node = new TrieNode[ALPHABET];
        }
    }

    public void insert(String path,Object value){
        char[] chars = path.toCharArray();
        TrieNode temp = head;
        for(int i = 0 ; i < chars.length ; i++){
            if(temp.node[chars[i]] == null){  //没有找到该字符，则在数组该位置上创建一个TrieNode
                temp.node[chars[i]] = new TrieNode();
            }
            temp = temp.node[chars[i]];  //移到刚创建的TrieNode上
            if(i == chars.length-1)  //当到了path末尾将value保存在TrieNode节点的value中
                temp.value = value;
        }
    }
    public T get(String path){
         char[] chars = path.toCharArray();
         T value = null;
         TrieNode temp = head;
         for(int i = 0 ; i < chars.length ; i ++) {
             if (temp.node[chars[i]] == null)
                 return null;
             temp = temp.node[chars[i]];
             if ( i == chars.length - 1 && temp.value != null) // 当path匹配完的那个node如果有value 则返回
                 value = (T) temp.value;
         }
         return value;
    }
}
