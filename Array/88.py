class Solution(object):
    def merge(self, nums1, m, nums2, n):
        """
        :type nums1: List[int]
        :type m: int
        :type nums2: List[int]
        :type n: int
        :rtype: None Do not return anything, modify nums1 in-place instead.
        """

        p, q, a = m-1, n-1, m+n-1
        while p >= 0 and q >= 0:
            if nums1[p] > nums2[q]:
                nums1[a] = nums1[p]
                p -=1
            else:
                nums1[a] = nums2[q]
                q-=1
            
            a-=1
        
        while q>=0:
            nums1[a] = nums2[q]
            a-=1
            q-=1
    
