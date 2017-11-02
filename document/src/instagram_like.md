# Group Instagram Like

## Get Like [/v1/instagram/media/shortcode/{shortcode}/likes{?afterCode}]

### Get Like API [GET]

#### Summary

* Get Like

+ Parameters

    + shortcode: Bauw16WhS2A (string, required) - Instagram shortcode
    + afterCode: hogehoge (string, optional) - endCursor

+ Response 200 (application/json)

    + Attributes
        + id (string)
        + shortcode (string)
        + edgeLikedBy (object)
            + count: 53 (number)
            + pageInfo (object)
                + endCursor: hogehoge (string)
                + hasNextPage: false (boolean)
            + edges (array)
                + (object)
                    + node (object)
                        + id (string)
                        + username (string)
                        + fullName (string)
                        + profilePicUrl (string)
                        + isVerified: false (boolean)
                        + followedByViewer: false (boolean)
                        + requestedByViewer: false (boolean)
