import java.util.Deque;

class Solution {
    public int calculate(String s) {
        int res = 0;
        int sign = 1;
        int num = 0;
        Deque<Integer> deque = new ArrayDeque<>();

        for(int i=0;i< s.length();i++){
            char c = s.charAt(i);
            if(c == ' ') continue;

            if(Character.isDigit(c)){
                num = num * 10 + (c - '0');
            }else if(c == '+'){
                res += num * sign;
                num = 0;
                sign = 1;
            }else if(c == '-'){
                res += num * sign;
                num = 0;
                sign = -1;
            }else if(c == '('){
                deque.push(res);
                deque.push(sign);
                num = 0;
                res = 0;
                sign = 1;
            }else if (c == ')'){
                res += sign * num;
                int prevSign = deque.pop();
                int prevRes = deque.pop();
                res = prevRes + prevSign * res;
                num = 0;
            }
        }

        res += sign * num; 

        return res;
    }
}