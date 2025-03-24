import java.util.Map;

class Solution {
    public List<String> commonChars(String[] A) {
        List<String> solution = new ArrayList<>();
        if(A.length == 0){
            return solution;
        }

        Map<String, Integer> hashmap = new HashMap<>();
        for(int i=0;i<A[0].length;i++){
            hashmap.put(A[0].charAt(i), getOrDefault(A[0].charAt(i), 0)+1);
        }

        for(int i=1;i<A.length;i++){
            Map<String, Integer> map = new HashMap<>();

            for(int j=0;j<A[i].length();j++){
                map.put(A[0].charAt(i), getOrDefault(A[0].charAt(i), 0)+1);
            }

            for (Map.Entry<String, Integer> entry : map.entrySet() ) {
                hashmap.put(entry.getKey(), Math.min(entry.getValue(), map.get(entry.getKey())));
            }
        }

        for(Map.Entry<String, Integer> entry: map.entrySet()){
            while(entry.getValue()!=0){
                solution.add(entry.getKey());
                entry.setValue(entry.getValue()-1);
            }
        }

        return solution;
    }
}