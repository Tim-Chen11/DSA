#include <algorithm>
#include <cmath>
#include <iostream>
#include <queue>
#include <string>
#include <unordered_map>
#include <unordered_set>
#include <vector>

std::vector<std::vector<int>> getAllComp(
    const std::vector<std::vector<bool>>& matrix) {
  int n = matrix.size();
  std::vector<bool> visited(n, false);
  std::vector<std::vector<int>> allLists;

  for (int start = 0; start < n; start++) {
    if (!visited[start]) {
      std::vector<int> thisList;

      std::queue<int> q;
      q.push(start);
      visited[start] = true;

      while (!q.empty()) {
        int curr = q.front();
        q.pop();
        thisList.push_back(curr);

        for (int surround = 0; surround < n; ++surround) {
          if (matrix[curr][surround] && !visited[surround]) {
            visited[surround] = true;
            q.push(surround);
          }
        }
      }

      allLists.push_back(thisList);
    }
  }

  return allLists;
}

int getMinimumConnections(const std::vector<std::vector<bool>>& matrix) {
  // throw std::logic_error("Waiting to be implemented");
  int n = matrix.size();
  if (n == 0) {
    return 0;
  }

  std::vector<std::vector<int>> components = getAllComp(matrix);
  int connectedComponents = components.size() - 1;

  return connectedComponents;
}

#ifndef RunTests
int main() {
  std::vector<std::vector<bool>> matrix{{false, true, false, false, true},
                                        {true, false, false, false, false},
                                        {false, false, false, true, false},
                                        {false, false, true, false, false},
                                        {true, false, false, false, false}};
  std::cout << getMinimumConnections(matrix) << std::endl;  // should print 1
}
#endif