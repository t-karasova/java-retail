<walkthrough-metadata>
  <meta name="title" content="Query tutorial" />
  <meta name="description" content="This tutorial shows you how to send a simple search query to the Retail service and analyze the response." />
  <meta name="component_id" content="593554" />
</walkthrough-metadata>

# Query tutorial

## Get started

This tutorial shows you how to send a simple search query to the Retail service and analyze the response.


<walkthrough-tutorial-duration duration="4"></walkthrough-tutorial-duration>

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

## Simple query request

The simple search request contains only the following required fields:
- `placement`—a resource name of the search engine placement.
- `visitor_id`—a unique identifier to track visitors.
- `query`—a raw search query or search phrase.

To check the results right away, you need to run a request on a products catalog.

1. Open
   <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchSimpleQuery.java" regex="TRY DIFFERENT QUERY PHRASES HERE">SearchSimpleQuery.java</walkthrough-editor-select-regex> to review the request.

2. To request the search service with a `Hoodie` query, run the following command in Terminal:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchSimpleQuery"
    ```

3. Note that the results contain a list of matched items returned by the Retail Search service.

    - `total_size` is the estimated total count of matched items.

    - `attribution_token` is a unique search token that enables accurate attribution of the search model performance.

    - `next_page_token` is a token that forwards to the next page in the search response. By default, the number of products per page is 100. If this field is omitted, there are no subsequent pages.

## Simple query: query phrase calibrating

Next, try to experiment with the query phrases.

1. Find the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchSimpleQuery.java" regex="TRY DIFFERENT QUERY PHRASES HERE">condition</walkthrough-editor-select-regex> and replace the value of `query_phrase` with the following samples:

    ```
    query_phrase = "Zip Hoodie"
    ```

   and
    ```
    query_phrase = "Unisex Zip Hoodie"
    ```

2. Run the following command in Terminal:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchSimpleQuery"
    ```

Adding one more word to the query phrase makes your request more accurate. You can expect fewer products in the response, and the most relevant products will appear on the top of the response list.

## Error handling

In case of sending some invalid data or if any of the required fields is missing in the request, the Search Service responds with an error message.
To find a complete list of the Search Request fields with their corresponding requirements, check the [Search Service references](https://cloud.google.com/retail/docs/reference/rpc/google.cloud.retail.v2#searchservice)

In this tutorial, you will get an error message when trying to request the Search Service without setting the `visitor_id`, which is a required field.

1. To check it, comment out a <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchSimpleQuery.java" regex="123456">line</walkthrough-editor-select-regex>: `search_request.visitor_id = "123456"`.

1. Run the following command in Terminal:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchSimpleQuery"
    ```

1. You should see the following error message:

    ```terminal
    google.api_core.exceptions.InvalidArgument: 400 Field "visitor_id" is a required field, but no value is found.
    ```

## Congratulations

<walkthrough-conclusion-trophy></walkthrough-conclusion-trophy>

You have completed the tutorial! We encourage you to test the search by yourself and try different combinations of different search queries.

<walkthrough-inline-feedback></walkthrough-inline-feedback>
