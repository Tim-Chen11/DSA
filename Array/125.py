class Solution(object):
    def isPalindrome(self, s):
        p, q = 0, len(s) - 1

        while p < q:
            while p < q and not s[p].isalnum():
                p += 1
            while p < q and not s[q].isalnum():
                q -= 1

            if s[p].lower() != s[q].lower():
                return False

            p += 1
            q -= 1

        return True
