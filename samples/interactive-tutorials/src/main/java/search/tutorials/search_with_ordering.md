<walkthrough-metadata>
  <meta name="title" content="Ordering tutorial"/>
  <meta name="description" content="This tutorial shows you how to order items in a search response." />
  <meta name="component_id" content="593554" />
</walkthrough-metadata>

# Ordering tutorial

## Get started

This tutorial shows you how to order items in a search response.

You can apply ordering to most of the product fields. To find the complete list of available fields, check the Retail API documentation.

And now, let's see how the product ordering works.

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

## Order by a single field: ordering expression

To use the ordering feature, you need to specify the field and the ordering direction. You can order by both the text and numeric fields.

1. Open <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithOrdering.java" regex="TRY DIFFERENT ORDERING EXPRESSIONS HERE">SearchWithOrdering.java</walkthrough-editor-select-regex>.

1. Order the search results by price when more expensive items come first. To do that, set the ordering expression as follows:

   ```order = 'price desc'```


1. Run the sample in the Terminal using the following command:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithOrdering"
    ```

As you can see, the results are now ordered by descending price.

## Order by a single field: product sorting

Next, change the ordering direction to show the cheapest products first.

1. Change the condition under the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithOrdering.java" regex="TRY DIFFERENT ORDERING EXPRESSIONS HERE">comment</walkthrough-editor-select-regex> to the following:

   ```order = 'price asc'``` or just ```order = 'price'```
   Those are equivalent expressions because ascending is the default ordering direction.

1. Run the sample in the Terminal using the command:

    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithOrdering"
    ```

You have sorted the products by ascending price.

## Order by multiple fields

You can order items by multiple fields using the comma-separated fields in order of priority, with fields having higher priority coming first.

To order items with equal values for higher-priority fields, use the lower-priority fields.

For example, **```price desc, discount desc```** orders items by their price first. The products with the same price will be ordered by a discount amount.

1. To try that, change the ordering expression to the next one under the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithOrdering.java" regex="TRY DIFFERENT ORDERING EXPRESSIONS HERE">comment</walkthrough-editor-select-regex>:
    ```
    order = 'price desc, discount'
    ```

   or

    ```
    order = 'brands, attributes.collection desc'
    ```

1. Run the code sample in the Terminal using the command:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithOrdering"
    ```

## Ordering: error handling

In case of sending some invalid data or if any of the required fields is missing in the request, the Search Service responds with an error message.
To find a complete list of the Search Request fields with their corresponding requirements, check the [Search Service references](https://cloud.google.com/retail/docs/reference/rpc/google.cloud.retail.v2#searchservice).

To check a list of ordering fields, use the [Retail API documentation](https://cloud.google.com/retail/docs/filter-and-order#order).

If you try to order the search results by the field that is not intended for ordering (for example, the `name` field), you will get an error message.

1. Change the variable `order` value under the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithOrdering.java" regex="TRY DIFFERENT ORDERING EXPRESSIONS HERE">comment</walkthrough-editor-select-regex> to the following:
   ```order = 'name desc'```

1. Run the code again:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithOrdering"
    ```

1. You should see the following error message:

   ```google.api_core.exceptions.InvalidArgument: 400 Invalid order syntax 'name desc'. Parsing order failed with error: Unsupported field in order: name. ```

## Congratulations

<walkthrough-conclusion-trophy></walkthrough-conclusion-trophy>

You have completed the tutorial! We encourage you to test the ordering by yourself, and try different combinations of various order expressions.

<walkthrough-inline-feedback></walkthrough-inline-feedback>
