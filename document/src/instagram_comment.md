# Group Instagram Comment

## Get Comment [/v1/instagram/media/shortcode/{shortcode}/comments{?afterCode}]

### Get Comment API [GET]

#### Summary

* Get Comment

+ Parameters

    + shortcode: Bauw16WhS2A (string, required) - Instagram shortcode
    + afterCode: hogehoge (string, optional) - endCursor

+ Response 200 (application/json)

    + Attributes
        + data (object)
            + shortcodeMedia (object)
                + edgeMediaToComment (object)
                    + count: 21 (number)
                    + pageInfo (object)
                        + endCursor: hogehoge (string)
                        + hasNextPage: false (boolean)
                    + edges (array)
                        + (object)
                            + node (object)
                                + id: 1634458525801721216 (string)
                                + text (string)
                                + createdAt: 1509062661 (number)
                                + owner (object)
                                    + id: 6039196059 (string)
                                    + profilePicUrl (string)
                                    + username (string)
        + status: ok (string)
