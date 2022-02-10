/*
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * [START retail_search_for_products_with_ordering]
 * Call Retail API to search for a products in a catalog,
 * order the results by different product fields.
 */

package search;

import com.google.cloud.retail.v2.SearchRequest;
import com.google.cloud.retail.v2.SearchResponse;
import com.google.cloud.retail.v2.SearchServiceClient;
import java.io.IOException;
import java.util.UUID;

public class SearchWithOrdering {

  public static void main(String[] args) throws IOException {
    // TODO(developer): Replace these variables before running the sample.
    String projectNumber = System.getenv("PROJECT_NUMBER");
    String defaultCatalogName =
        String.format("projects/%s/locations/global/catalogs/default_catalog", projectNumber);
    String defaultSearchPlacementName = defaultCatalogName + "/placements/default_search";

    search(defaultSearchPlacementName);
  }

  public static SearchResponse search(String defaultSearchPlacementName) throws IOException {
    // TRY DIFFERENT ORDER BY EXPRESSION HERE:
    String order = "price desc";
    String queryPhrase = "Hoodie";
    int pageSize = 10;
    String visitorId = UUID.randomUUID().toString();

    SearchRequest searchRequest =
        SearchRequest.newBuilder()
            .setPlacement(defaultSearchPlacementName)
            .setQuery(queryPhrase)
            .setOrderBy(order)
            .setVisitorId(visitorId)
            .setPageSize(pageSize)
            .build();
    System.out.println("Search request: " + searchRequest);

    try (SearchServiceClient client = SearchServiceClient.create()) {
      SearchResponse searchResponse = client.search(searchRequest).getPage().getResponse();
      System.out.println("Search response: " + searchResponse);

      return searchResponse;
    }
  }
}

// [END retail_search_for_products_with_ordering]