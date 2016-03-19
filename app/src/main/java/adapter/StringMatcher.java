package adapter;

/**
 * Created by Administrator on 2016/3/19.
 */
public class StringMatcher {
    //value: item文本
    //keyword: 索引表的字符
    public static boolean match(String value,String keyword) {
        if(value == null || keyword == null) {
            return false;
        }
        if(keyword.length() > value.length()) {
            return false;
        }
        int i = 0 , j = 0;
        do{
            if(keyword.charAt(j) == value.charAt(i)) {
                i ++;
                j ++;
            } else if(j > 0){
                break;
            } else {
                i ++;
            }
        }while (i < value.length() && j < keyword.length());
        return (j == keyword.length());
    }
}
