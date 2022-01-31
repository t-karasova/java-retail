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

package init;

import com.google.api.gax.paging.Page;
import com.google.api.gax.rpc.PermissionDeniedException;
import com.google.cloud.retail.v2.DeleteProductRequest;
import com.google.cloud.retail.v2.ListProductsRequest;
import com.google.cloud.retail.v2.Product;
import com.google.cloud.retail.v2.ProductServiceClient;
import com.google.cloud.retail.v2.ProductServiceClient.ListProductsPagedResponse;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;

public final class RemoveTestResources {

  /**
   * This variable describes project number getting from environment variable.
   */
  private static final String PROJECT_NUMBER = System.getenv("PROJECT_NUMBER");

  /**
   * This variable describes bucket name from the environment variable.
   */
  private static final String BUCKET_NAME = System.getenv("BUCKET_NAME");

  /**
   * This variable describes default catalog name.
   */
  private static final String DEFAULT_CATALOG = String.format(
      "projects/%s/locations/global/catalogs/default_catalog/"
          + "branches/default_branch", PROJECT_NUMBER);

  /**
   * This variable describes Storage.
   */
  private static final Storage STORAGE = StorageOptions.newBuilder()
      .setProjectId(PROJECT_NUMBER)
      .build().getService();

  private RemoveTestResources() {
  }

  /**
   * Delete bucket from GCS.
   */
  public static void deleteBucket() {
    try {
      Bucket bucket = STORAGE.get(BUCKET_NAME);

      if (bucket != null) {
        bucket.delete();
      }
    } catch (StorageException e) {
      System.out.printf("Bucket is not empty. Deleting objects from bucket.%n");

      deleteObjectsFromBucket(STORAGE.get(BUCKET_NAME));

      System.out.printf("Bucket %s was deleted.",
          STORAGE.get(BUCKET_NAME).getName());
    }

    if (STORAGE.get(BUCKET_NAME) == null) {
      System.out.printf("Bucket '%s' already deleted.%n", BUCKET_NAME);
    }
  }

  /**
   * Delete objects from GCS bucket.
   *
   * @param bucket target bucket.
   */
  public static void deleteObjectsFromBucket(final Bucket bucket) {
    Page<Blob> blobs = bucket.list();

    for (Blob blob : blobs.iterateAll()) {
      blob.delete();
    }

    System.out.printf("All objects are deleted from GCS bucket %s%n",
        bucket.getName());
  }

  /**
   * Delete all products from catalog.
   *
   * @throws IOException from the called method.
   */
  public static void deleteAllProducts() throws IOException {
    System.out.println("Deleting products in process, please wait...");

    ProductServiceClient productServiceClient = ProductServiceClient.create();

    ListProductsRequest listRequest = ListProductsRequest.newBuilder()
        .setParent(DEFAULT_CATALOG)
        .build();

    ListProductsPagedResponse products = productServiceClient.listProducts(
        listRequest);

    int deleteCount = 0;

    for (Product product : products.iterateAll()) {
      DeleteProductRequest deleteRequest = DeleteProductRequest.newBuilder()
          .setName(product.getName())
          .build();

      try {
        productServiceClient.deleteProduct(deleteRequest);
        deleteCount++;
      } catch (PermissionDeniedException e) {
        System.out.println(
            "Ignore PermissionDenied in case the product does not exist "
                + "at time of deletion.");
      }
    }

    System.out.printf("%s products were deleted from %s%n", deleteCount,
        DEFAULT_CATALOG);
  }

  /**
   * Executable class.
   *
   * @param args command line arguments.
   */
  public static void main(final String[] args) throws IOException {
    deleteAllProducts();

    deleteBucket();
  }
}