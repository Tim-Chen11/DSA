class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
	List<List<Integer>> result = new ArrayList<>();
	Arrays.sort(nums);

	for (int i = 0; i < nums.length; i++) {
		if (nums[i] > 0) {
			return result;
		}
		if (i > 0 && nums[i] == nums[i - 1]) {
			continue;
		}

		HashSet<Integer> set = new HashSet<>();
		for (int j = i + 1; j < nums.length; j++) {
			if (j > i + 2 && nums[j] == nums[j - 1] && nums[j - 1] == nums[j - 2]) {
				continue;
			}

			int c = -nums[i] - nums[j];
			if (set.contains(c)) {
				result.add(Arrays.asList(nums[i], nums[j], c));
				set.remove(c);
			} else {
				set.add(nums[j]);
			}
		}
	}
	return result;
    }
}

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
	// 找出a + b + c = 0
        // a = nums[i], b = nums[left], c = nums[right]
        for (int i = 0; i < nums.length; i++) {
	    // 排序之后如果第一个元素已经大于零，那么无论如何组合都不可能凑成三元组，直接返回结果就可以了
            if (nums[i] > 0) { 
                return result;
            }

            if (i > 0 && nums[i] == nums[i - 1]) {  // 去重a
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;
            while (right > left) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
		    // 去重逻辑应该放在找到一个三元组之后，对b 和 c去重
                    while (right > left && nums[right] == nums[right - 1]) right--;
                    while (right > left && nums[left] == nums[left + 1]) left++;
                    
                    right--; 
                    left++;
                }
            }
        }
        return result;
    }
}