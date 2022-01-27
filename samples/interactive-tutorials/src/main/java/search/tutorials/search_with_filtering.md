<walkthrough-metadata>
  <meta name="title" content="Filter tutorial" />
  <meta name="description" content="In this tutorial you will learn some examples of product filtering." />
  <meta name="component_id" content="593554" />
</walkthrough-metadata>

# Filter tutorial

## Get started

Filtering in the Retail service is a powerful and convenient search feature.
It lets you fine-tune search requests according to your or your customer's needs.
You can:

- Filter by single or multiple fields.
- Filter by text or numeric fields, or both.
- Use an expression language to construct a predicate for each field.
- Combine different expressions using logical operators.

In this tutorial you will learn some examples of product filtering.

<walkthrough-tutorial-duration duration="7"></walkthrough-tutorial-duration>

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

## Filter by text field: filtering expression

You can write a simple expression that applies to the text field and looks like this:

```(textual_field,":", "ANY", "(",literal,{",",literal},")"```

Function `ANY` returns true if the field contains any literals.

An example of such an expression is
```'(colorFamily: ANY("Black"))'```

1. To see the whole request with the filtering applied, open
   <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithFiltering.java" regex="TRY DIFFERENT FILTER EXPRESSIONS HERE">SearchWithFiltering.java</walkthrough-editor-select-regex>.

1. Run the following command in Terminal:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithFiltering"
    ```

1. Note that the results contain only items that satisfy the filtering expression.

## Filter by text field: Use case

Now you can try filtering by a text field yourself in the Cloud Shell environment.

1. To do that, replace the condition under the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithFiltering.java" regex="TRY DIFFERENT FILTER EXPRESSIONS HERE">comment</walkthrough-editor-select-regex> with one of the following samples:
    ```
    filter = '(brands: ANY("YouTube"))'
    ```

   or
    ```
    filter = '(colorFamily: ANY("White","Gray"))'
    ```
2. Run the following command in Terminal:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithFiltering"
    ```

For a complete list of the text fields you can apply filters to, see the [Retail API documentation](https://cloud.google.com/retail/docs/filter-and-order#filter).

## Filter by a numeric field: IN range

To filter by a numeric field, you can write the filtering expression in two ways:
- To check whether the field value is within a range, use the function `IN`.
- To compare a field value with a double value, use operators `<=`,  `<`,  `>=`, `>` and `=`.

1. To use the function `IN` to search for products with a price between $15 and $45, change the filter expression under the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithFiltering.java" regex="TRY DIFFERENT FILTER EXPRESSIONS HERE">comment</walkthrough-editor-select-regex> to the following:


    ```
    filter = 'price: IN(15.0, 45.0)'
    ```

1. Run the following command in Terminal:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithFiltering"
    ```

1. Check the search response. Now it contains only items with prices in the range of $15 to $45.

To see the complete list of the numeric fields you can apply filters to, see the [Retail API documentation](https://cloud.google.com/retail/docs/filter-and-order#filter).

## Filter by a numeric field: comparison operators

All comparison operators (`<=`,  `<`,  `>=`, `>` and `=`) are available for filtering expressions.

1. Change the filter expression under the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithFiltering.java" regex="TRY DIFFERENT FILTER EXPRESSIONS HERE">comment</walkthrough-editor-select-regex> to the following:
    ```
    filter = 'price >= 15.0 AND price < 45.0'
    ```

1. Run the following command in Terminal:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithFiltering"
    ```

1. Check the search response. Now it contains only items with prices in the range between $15 and $45.

## Filter by multiple fields

To filter the search results by multiple fields, you can combine the expressions with `AND` or `OR` operators:

```filter = expression { " AND " | " OR " } expression```

1. Change the filter expression under the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithFiltering.java" regex="TRY DIFFERENT FILTER EXPRESSIONS HERE">comment</walkthrough-editor-select-regex> to the following:
    ```
    filter = '(categories: ANY("Apparel")) AND (price: IN(30.0, *))'
    ```

2. Run the following command in Terminal:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithFiltering"
    ```

3. Check the search response. Now it contains only items from the `Apparel` category with prices more than $30.

## Error handling

In case of sending some invalid data or if any of the required fields is missing in the request, the Search Service responds with an error message.

To find a complete list of the Search Request fields with their corresponding requirements, check the [Search Service references](https://cloud.google.com/retail/docs/reference/rpc/google.cloud.retail.v2#searchservice)

To check a list of text and numeric fields that support filtering, use the [Retail API documentation](https://cloud.google.com/retail/docs/filter-and-order#filter)

If you try to filter the search results by a field that is not intended for filtering, for example, the `name` field, you will get an error message.

1. Change the variable `filter` value under the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithFiltering.java" regex="TRY DIFFERENT FILTER EXPRESSIONS HERE">comment</walkthrough-editor-select-regex> to the following:

    ```
    filter = '(name: ANY("some_random"))'
    ```

2. Run the following command in Terminal:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithFiltering"
    ```

3. You should see the following error message:

    ```terminal
    google.api_core.exceptions.InvalidArgument: 400 Invalid filter syntax '(name: ANY("some_random"))'. Parsing filter failed with error: Unsupported field "name" on ":" operator.
    ```

## Congratulations

<walkthrough-conclusion-trophy></walkthrough-conclusion-trophy>

You have completed the tutorial! We encourage you to test the filtering by yourself and try different combinations of various filter expressions.

<walkthrough-inline-feedback></walkthrough-inline-feedback>
