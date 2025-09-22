class Solution {
    public int lengthOfLastWord(String s) {
        String[] parts = s.trim().split(" ");
        return parts[parts.length - 1].length();
    }
}


class Solution {
    public int lengthOfLastWord(String s) {
        int i = s.length() - 1;
        int length = 0;

        // skip trailing spaces
        while (i >= 0 && s.charAt(i) == ' ') {
            i--;
        }

        // count last word
        while (i >= 0 && s.charAt(i) != ' ') {
            length++;
            i--;
        }

        return length;
    }
}
