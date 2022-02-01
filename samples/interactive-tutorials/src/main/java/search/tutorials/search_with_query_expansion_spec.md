<walkthrough-metadata>
  <meta name="title" content="Query Expansion tutorial" />
  <meta name="description" content="How to enable the query expansion feature to increase the efficiency for search for ambiguous or long-tail query terms." />
  <meta name="component_id" content="593554" />
</walkthrough-metadata>

# Query Expansion tutorial

## Get started

This tutorial shows you how to enable the query expansion feature to increase the efficiency for search for ambiguous or long-tail query terms.

Disabling the query expansion results in using only the exact search query, even if the total number of search results is zero.

You can enable the query expansion feature and let the Google Retail Search build an automatic query expansion.

You can also pin unexpanded products so that they always appear at the top of search results followed by products enlisted via expansion.

This feature helps you to enhance a customer experience.

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

2. Check that the Retail API is enabled for your Project in the [Admin Console](https://console.cloud.google.com/ai/retail/).

### Set up authentication

To run a code sample from the Cloud Shell, you need to be authenticated using the service account credentials.

1. Login with your user credentials.
    ```bash
    gcloud auth login
    ```

2. Type `Y` and press **Enter**. Click the link in Terminal. A browser window should appear asking you to log in using your Gmail account.

3. Provide the Google Auth Library with access to your credentials and paste the code from the browser to the Terminal.

4. Upload your service account key JSON file and use it to activate the service account:

    ```bash
    gcloud iam service-accounts keys create ~/key.json --iam-account <YOUR_SERVICE_ACCOUNT_EMAIL>
    ```

    ```bash
    gcloud auth activate-service-account --key-file  ~/key.json
    ```

5. Set key as the GOOGLE_APPLICATION_CREDENTIALS environment variable to be used for requesting the Retail API:
    ```bash
    export GOOGLE_APPLICATION_CREDENTIALS=~/key.json
    ```

**Note**: Click the copy button on the side of the code box to paste the command in the Cloud Shell terminal and run it.

### Set the PROJECT_NUMBER environment variable

Because you are going to run the code samples in your own Google Cloud project, you should specify the **project_number** as an environment variable. It will be used in every request to the Retail API.

1. You can find the ```project_number``` in the Project Info card displayed on **Home/Dashboard**.

2. Set the environment variable with the following command:
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

## Query expansion: AUTO condition

1. Open <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/src/main/java/search/SearchWithQueryExpansionSpec.java" regex="TRY DIFFERENT QUERY EXPANSION CONDITION HERE">SearchWithQueryExpansionSpec.java</walkthrough-editor-select-regex>.

2. Here you can see the query expansion condition set with value `AUTO`. The setting enables the query expansion feature and expands the search results.

3. Run the sample in the Terminal using the command:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithQueryExpansionSpec"
    ```

As you can see, the results contain products that do not exactly match the search query but are close to it.

## Query expansion: DISABLED condition

Change the condition value to `DISABLED`.

1. Change the condition under the <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/src/main/java/search/SearchWithQueryExpansionSpec.java" regex="TRY DIFFERENT QUERY EXPANSION CONDITION HERE">comment</walkthrough-editor-select-regex> to the following:

   ```terminal
   condition = Condition.DISABLED
   ```

2. Run the sample in the Terminal using the command:

    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithQueryExpansionSpec"
    ```

As you can see, the results contain only items that exactly match the search query.

## Congratulations

<walkthrough-conclusion-trophy></walkthrough-conclusion-trophy>

You have completed the tutorial! We encourage you to test the query expansion by yourself and try different search phrases with and without query expansion.

<walkthrough-inline-feedback></walkthrough-inline-feedback>
