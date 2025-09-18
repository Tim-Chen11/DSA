#include <unordered_set>
#include <iostream>
#include <string>
using namespace std;

class Solution {
public:
    int lengthOfLongestSubstring(string s) {
        int count = 0;
        int left = 0;
        unordered_set<char> set;

        for(int right = 0; right < s.length(); right++){
            while(set.count(s[right])){
                set.erase(s[left]);
                left+=1;
            }

            set.insert(s[right]);

            count = max(count, right-left+1);
        }

        return count;
    }
};