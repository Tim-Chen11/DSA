#include <iostream>
#include <sstream>
#include <vector>
#include <unordered_map>
using namespace std;

class Solution {
public:
    bool wordPattern(string pattern, string s) {
        vector<string> words = split(s);
        if (pattern.size() != words.size()) return false;

        unordered_map<char, string> charToWord;
        unordered_map<string, char> wordToChar;

        for (int i = 0; i < pattern.size(); i++) {
            char c = pattern[i];
            string w = words[i];

            if (charToWord.count(c) && charToWord[c] != w) return false;
            if (wordToChar.count(w) && wordToChar[w] != c) return false;

            charToWord[c] = w;
            wordToChar[w] = c;
        }
        return true;
    }

private:
    // Helper to split a string by spaces
    vector<string> split(string s) {
        vector<string> result;
        string word;
        stringstream ss(s);
        while (ss >> word) {
            result.push_back(word);
        }
        return result;
    }
};