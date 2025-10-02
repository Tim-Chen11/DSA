import java.util.Deque;

class Solution {
    public int calculate(String s) {
        Deque<Integer> dequeu = new ArrayDeque<>();
        int res =0;
        int num = 0;
        char ops = '+';

        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);

            if(Character.isDigit(c)){
                num = num*10+(int)(c-'0');
            }

            if((!Character.isDigit(c) && c!=' ' )|| i==s.length()-1){                
                if(ops == '+'){
                    dequeu.push(+num);
                }else if(ops =='-'){
                    dequeu.push(-num);
                }else if(ops =='*'){
                    num = (int)dequeu.pop()*num;
                    dequeu.push(num);
                }else if(ops =='/'){
                    num = (int)dequeu.pop()/num;
                    dequeu.push(num);
                }

                ops = c;
                num = 0;
            }
        }

        while(!dequeu.isEmpty()){
            res += dequeu.pop();
        }

        return res;
        
    }
}