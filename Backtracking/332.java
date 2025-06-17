class Solution {
    private Deque<String> res;
    private Map<String, Map<String, Integer>> map;

    private boolean backTracking(int ticketNum){
        if(res.size() == ticketNum + 1){
            return true;
        }
        String last = res.getLast();
        if(map.containsKey(last)){//防止出现null
            for(Map.Entry<String, Integer> target : map.get(last).entrySet()){
                int count = target.getValue();
                if(count > 0){
                    res.add(target.getKey());
                    target.setValue(count - 1);
                    if(backTracking(ticketNum)) return true;
                    res.removeLast();
                    target.setValue(count);
                }
            }
        }
        return false;
    }

    public List<String> findItinerary(List<List<String>> tickets) {
        map = new HashMap<String, Map<String, Integer>>();
        res = new LinkedList<>();
        for(List<String> t : tickets){
            Map<String, Integer> temp;
            if(map.containsKey(t.get(0))){
                temp = map.get(t.get(0));
                temp.put(t.get(1), temp.getOrDefault(t.get(1), 0) + 1);
            }else{
                temp = new TreeMap<>();//升序Map
                temp.put(t.get(1), 1);
            }
            map.put(t.get(0), temp);

        }
        res.add("JFK");
        backTracking(tickets.size());
        return new ArrayList<>(res);
    }
}

class Solution {
    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, List<String>> graph = new HashMap<>();

        for (List<String> ticket : tickets) {
            String departure = ticket.get(0);
            String arrival = ticket.get(1);

            graph.computeIfAbsent(departure, k -> new ArrayList<>()).add(arrival);
        }

        for (List<String> destinations : graph.values()) {
            destinations.sort(Collections.reverseOrder());
        }

        List<String> newItinerary = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        stack.push("JFK");

        while (!stack.isEmpty()) {
            String currentAirport = stack.peek();

            if (graph.containsKey(currentAirport) && !graph.get(currentAirport).isEmpty()) {
                stack.push(graph.get(currentAirport).remove(graph.get(currentAirport).size() - 1));
            } else {
                newItinerary.add(stack.pop());
            }
        }

        Collections.reverse(newItinerary);
        return newItinerary;        
    }
}