# Search with faceting tutorial

## Get started

Facets are product attribute filters (for example, brand or color) that helps
your users further narrow down their search results. Facet values usually are
placed on the UI search page alongside search results, allowing a user to select
and filter search results by facet values.

Facets are based on attributes you have provided for a product, such as color,
size, brand, or custom attribute. The Retail can use each product attribute
as facet key only if this attribute is set to indexable.

In this tutorial you will learn some examples of getting facets.

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

To run a code sample from the Cloud Shell, you need to authenticate. To do this, use the Application Default Credentials.

1. Set your user credentials to authenticate your requests to the Retail API

    ```bash
    gcloud auth application-default login
    ```

1. Type `Y` and press **Enter**. Click the link in Terminal. A browser window should appear asking you to log in using your Gmail account.

1. Provide the Google Auth Library with access to your credentials and paste the code from the browser to the Terminal.

1. Run the code sample and check the Retail API in action.

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

## Getting textual facets, facet_key = "colorFamilies"

To include facets to the search response, you provide a **```FacetSpec```** in
the search request body, explicitly passing product attributes as facets.

1. In the FacetSpec the only required field is the **```facet_key```**. It
   defines the product attribute to be used as facet. You can set here ether
   textual o numerical field name. The full list of the facet keys you can find
   in the [Retail documentation.](https://cloud.google.com/retail/docs/reference/rpc/google.cloud.retail.v2#facetkey)

2. Now open <walkthrough-editor-select-regex filePath="cloudshell_open/interactive-tutorials/search/SearchWithFacetSpec.java" regex="boost.*0">SearchWithFacetSpec.java</walkthrough-editor-select-regex>.

   In the initial request, the textual facet "colorFamilies" is going to be returned.

   So the SearchRequest is looking like this:
   ```
    placement: "projects/945579214386/locations/global/catalogs/default_catalog/placements/default_search"
    query: "Tee"
    visitor_id: "123456"
    page_size: 10
    facet_specs {
    facet_key {
      key: "colorFamilies"
      }
    }
    ```

## Getting textual facets. Result analyse

1. Run the sample in a terminal with the following command:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithFacetSpec"
    ```
1. Check the response contains the object **```facets```**:

```
facets {
  key: "colorFamilies"
  values {
    value: "Black"
    count: 16
  }
  values {
    value: "Blue"
    count: 8
  }
  values {
    value: "Gray"
    count: 4
  }
  values {
    value: "Green"
    count: 14
  }
  values {
    value: "Navy"
    count: 5
  }
  values {
    value: "Red"
    count: 4
  }
  values {
    value: "White"
    count: 4
  }
}
```

## Getting textual facets, facet_key = "brands".

1. Next, change the value of the field **facet_key** and run the code sample again:

```facetKey = "brands"```

1. Now you can check the results. The **facets** object now contains barnds:
```
facets {
  key: "brands"
  values {
    value: "#IamRemarkable"
    count: 3
  }
  values {
    value: "Android"
    count: 12
  }
  values {
    value: "Google"
    count: 105
  }
  values {
    value: "Google Cloud"
    count: 1
  }
  values {
    value: "Stan and Friends"
    count: 2
  }
  values {
    value: "YouTube"
    count: 7
  }
}
```
## Getting numerical facets

To  get the facets for the numerical field, you should specify the intervals of
values for each faceting field.

Let's modify the ```SearchRequest``` to get price facet with two price intervals: $(0 - 20.0] and $(21.0 - 50]

Define the Interval in the **```getSearchRequest()```**, add the following code:
```
    interval1 = Interval()
    interval1.minimum = 0.0
    interval1.maximum = 25.0
    interval2 = Interval()
    interval2.minimum = 26.0
    interval2.maximum = 50.0
```

Add the **```intervals```** field to the ```facet_key```:

```
facet_key.intervals = [interval1, interval2]
```

Next, change the ```facet_key``` value in the ```search()``` function:
```
facetKey = "price"
```

## Getting numerical facets. Result analyse

1. Run the sample in a terminal with the following command:
    ```bash
    mvn compile exec:java -Dexec.mainClass="search.SearchWithFacetSpec"
    ```
1. Check the response contains the object **```facets```**:

```
facets {
  key: "price"
  values {
    interval {
      minimum: 0.0
      maximum: 25.0
    }
    count: 96
  }
  values {
    interval {
      minimum: 26.0
      maximum: 50.0
    }
    count: 35
  }
}
```

## Congratulations

<walkthrough-conclusion-trophy></walkthrough-conclusion-trophy>

You have completed the tutorial! We encourage you to test the faceting feature by yourself and try different facet_keys.

<walkthrough-inline-feedback></walkthrough-inline-feedback>
  