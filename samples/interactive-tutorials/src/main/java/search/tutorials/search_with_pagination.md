<walkthrough-metadata>
  <meta name="title" content="Pagination tutorial"/>
  <meta name="description" content="Using pagination, you can view and navigate the search results effortlessly." />
  <meta name="component_id" content="593554" />
</walkthrough-metadata>

# Pagination tutorial

## Get started

Using pagination, you can view and navigate the search results effortlessly. Moreover, it decreases both the lookup time and the size of responses.

This tutorial shows you how to control pagination in your search request.

There are three fields in the search request that give you all the possibilities of navigating through the search results:
- **```page_size```**
- **```next_page_token```**
- **```offset```**

This tutorial describes how each of them works.

<walkthrough-tutorial-duration duration="5"></walkthrough-tutorial-duration>

## Get started with Google Cloud Retail

This step is required if this is the first Retail API Tutorial you run.
Otherwise, you can skip it.

### Select your project and enable the Retail API

Google Cloud organizes resources into projects. This lets you
collect all the related resources for a single application in one place.

If you don't have a Google Cloud project yet or you're not the owner of an existing one, you can
[create a new project](https://console.cloud.google.com/projectcreate).

After the project is created, set your PROJECT_ID to a ```project``` variable.
1. Run the following command in Terminal:
    ```bash
    gcloud config set project <YOUR_PROJECT_ID>
    ```

1. Check that the Retail API is enabled for your Project in the [Admin Console](https://console.cloud.google.com/ai/retail/).

### Set up authentication

To run a code sample from the Cloud Shell, you need to be authenticated using the service account credentials.

1. Login with your user credentials.
    ```bash
    gcloud auth login
    ```

1. Type `Y` and press **Enter**. Click the link in Terminal. A browser window should appear asking you to log in using your Gmail account.

1. Provide the Google Auth Library with access to your credentials and paste the code from the browser to the Terminal.

1. Upload your service account key JSON file and use it to activate the service account:

    ```bash
    gcloud iam service-accounts keys create ~/key.json --iam-account <YOUR_SERVICE_ACCOUNT_EMAIL>
    ```

    ```bash
    gcloud auth activate-service-account --key-file  ~/key.json
    ```

1. Set key as the GOOGLE_APPLICATION_CREDENTIALS environment variable to be used for requesting the Retail API:
    ```bash
    export GOOGLE_APPLICATION_CREDENTIALS=~/key.json
    ```

**Note**: Click the copy button on the side of the code box to paste the command in the Cloud Shell terminal and run it.

### Set the PROJECT_NUMBER environment variable

Because you are going to run the code samples in your own Google Cloud project, you should specify the **project_number** as an environment variable. It will be used in every request to the Retail API.

1. You can find the ```project_number``` in the Project Info card displayed on **Home/Dashboard**.

1. Set the environment variable with the following command:
    ```bash
    export PROJECT_NUMBER=<YOUR_PROJECT_NUMBER>
    ```

### Google Cloud Retail libraries

You can find the Java Google Retail library is described
[here](https://googleapis.dev/java/google-cloud-retail/latest/index.html)

## Clone the Retail code samples

This step is required if this is the first Retail API Tutorial you run.
Otherwise, you can skip it.

Clone the Git repository with all the code samples to learn the Retail features and check them in action.

<!-- TODO(ianan): change the repository link -->
1. Run the following command in the Terminal:
    ```bash
    git clone https://github.com/t-karasova/java-retail.git
    ```

The code samples for each of the Retail services are stored in different directories.

2. Go to the `interactive-tutorials` directory. It's our starting point to run more commands.
    ```bash
    cd java-retail/samples/interactive-tutorials
    ```

## Page size

The ```page_size``` request field lets you limit the number of items in the search response.

1. To view the request with ```page_size```, open <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithPagination.java" regex="TRY DIFFERENT PAGINATION PARAMETERS HERE">SearchWithPagination.java</walkthrough-editor-select-regex>.

2. Run the sample in a terminal using the command:

    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithPagination"
    ```

As you can see now, the results contain the exact number of products you have set as the page size.

The **```total_size```** is not equal to the page size; it's the number of items matching the search query, and it doesn't change as you adjust the number of products per page.

## Next page token

After you have received a response in the previous step, you can request the next page.

You need to receive the ```next_page_token```, set it to a request field ```page_token```, and call the Search service again.

1. Find the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithPagination.java" regex="PASTE CALL WITH NEXT PAGE TOKEN HERE">comment</walkthrough-editor-select-regex> and paste this piece of code:

    ```
        next_page_token = search_response_first_page.next_page_token
        search_request_next_page = get_search_request("Hoodie", page_size, offset, next_page_token)
        search_response_next_page = get_search_service_client().search(search_request_next_page)

        print("---next page search results---")
        print(search_response_next_page)
    ```

1. Run the code sample again:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithPagination"
    ```

You can see the next page of <page_size> products in the response.

The field **```next_page_token```** has a value that forwards you to the next page. You can use this field in further results navigation.

## Offset

In other cases, instead of navigating from page to page or getting results with top relevance, you can directly jump to a particular position using the offset.

You have requested the second page with 6 products per page using ```next_page_token``` in the previous step .

1. To reproduce the same effect using <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithPagination.java" regex="offset = \d">offset</walkthrough-editor-select-regex>, configure the field ```page_size``` with the same value, which is 6.
1. Perform a small calculation to get the offset value:

   `offset = 6 * (2 - 1) = 6`

   where 6 is a page size, and 2 is the page number of the page you would like to switch to.

## Offset use case

1. Find the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithPagination.java" regex="PASTE CALL WITH OFFSET HERE">comment</walkthrough-editor-select-regex> and paste this piece of code:
    ```
        offset = 6
        search_request_second_page = get_search_request("Hoodie", page_size, offset, page_token)
        search_response_second_page = get_search_service_client().search(search_request_second_page)

        print("---second page search results---")
        print(search_response_second_page)
    ```

1. Run the code sample again:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithPagination"
    ```

1. Take a look at both `next page search results` and `second page search results`. You can compare the lists of received products using both the `next_page_token` and `offset`, which should be equal.

Now you know how the offset works. Perform the calculation one more time to make it clear.

1. If you want to jump to the seventh page with a page size of 12, the offset value you need to set should be calculated this way:

   `offset = 12 * (7 - 1) = 72`

## Error handling

In case of sending some invalid data or if any of the required fields is missing in the request, the Search Service responds with an error message.
To find a complete list of the Search Request fields with their corresponding requirements, check the [Search Service references](https://cloud.google.com/retail/docs/reference/rpc/google.cloud.retail.v2#searchservice)

If you try to request the Search Service with a negative page size, you get an error message.

1. Change the value of the variable ```page_size``` to any negative value and run the code one more time.
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithPagination"
    ```

1. You should see the following error message:

    ```terminal
    google.api_core.exceptions.InvalidArgument: 400 `page_size` must be nonnegative, but is set to -6.
    ```

## Congratulations

<walkthrough-conclusion-trophy></walkthrough-conclusion-trophy>

You have completed the tutorial! We encourage you to test the pagination by yourself right here in the Cloud Shell environment using different combinations of values for pagination parameters.

<walkthrough-inline-feedback></walkthrough-inline-feedback>
