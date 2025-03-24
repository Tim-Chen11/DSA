import java.util.Map;

class Solution {
    public boolean isAnagram(String s, String t) {
        Map<String, Integer> map = new HashMap<>();
        for(int i=0;i<s.length();i++){
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0)+1);
        }

        for(int i=0;i<s.length();i++){
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0)-1);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if(entry.getValue()!=0){
                return false;
            }
        }
        return true;
    }
}