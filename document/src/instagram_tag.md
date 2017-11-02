# Group Instagram Tag

 Instagram Tag

## Get Tag Info [/v1/instagram/tags/{tagName}]

### Get Tag Info API [GET]

#### Summary

* Get Tag Info

+ Parameters

    + tagName: idonotlikefashion (string, required) - Instagram HashTag Name

+ Response 200 (application/json)

    + Attributes
        + media (object)
            + nodes (array)
                + (object)
                    + commentsDisabled: false (boolean)
                    + id: 1634458525801721216 (string)
                    + dimensions (object)
                        + height: 1080 (number)
                        + width: 1080 (number)
                    + owner (object)
                        + id: 6039196059 (string)
                    + thumbnailSrc: https://hogehoge.com (string)
                    + isVideo: false (boolean)
                    + code: Bauw16WhS2A (string)
                    + date: 1509062661 (number)
                    + displaySrc: https://hogehoge.com (string)
                    + caption: hogehoge (string)
                    + comments (object)
                        + count: 2 (number)
                    + likes (object)
                        + count: 51 (number)
                    + videoViews: 0 (number, optional)
            + count (number)
            + pageInfo (object)
                + endCursor: hogehoge (string)
                + hasNextPage: true (boolean)
        + topPosts (object)
            + nodes (array)
                + (object)
                    + commentsDisabled: false (boolean)
                    + id: 1634458525801721216 (string)
                    + dimensions (object)
                        + height: 1080 (number)
                        + width: 1080 (number)
                    + owner (object)
                        + id: 6039196059 (string)
                    + thumbnailSrc: https://hogehoge.com (string)
                    + isVideo: false (boolean)
                    + code: Bauw16WhS2A (string)
                    + date: 1509062661 (number)
                    + displaySrc: https://hogehoge.com (string)
                    + caption: hogehoge (string)
                    + comments (object)
                        + count: 2 (number)
                    + likes (object)
                        + count: 51 (number)
                    + videoViews: 0 (number, optional)

## Get Tag Media [/v1/instagram/tags/{tagName}/media{?afterCode}]

### Get Tag Media API [GET]

#### Summary

* Get Tag Media

+ Parameters

    + tagName: idonotlikefashion (string, required) - Instagram HashTag Name
    + afterCode: hogehoge (string, optional) - endCursor

+ Response 200 (application/json)

    + Attributes
        + data (object)
            + hashtag (object)
                + name: idonotlikefashion (string)
                + edgeHashtagToMedia (object)
                    + count: 21 (number)
                    + pageInfo (object)
                        + endCursor: hogehoge (string)
                        + hasNextPage: false (boolean)
                    + edges (array)
                        + (object)
                            + node (object)
                                + commentsDisabled: false (boolean)
                                + id: 1634458525801721216 (string)
                                + shortcode: Bauw16WhS2A (string)
                                + thumbnailSrc: https://hogehoge.com (string)
                                + takenAtTimestamp: 1509062661 (number)
                                + displayUrl: https://hogehoge.com (string)
                                + isVideo: false (boolean)
                                + edgeMediaToCaption (object)
                                    + edges (array)
                                        + (object)
                                            + node (object)
                                                + text (string)
                                + edgeMediaToComment (object)
                                    + count: 2 (number)
                                + dimensions (object)
                                    + height: 1080 (number)
                                    + width: 1080 (number)
                                + edgeLikedBy (object)
                                    + count: 2 (number)
                                + owner (object)
                                    + id: 6039196059 (string)
        + status: ok (string)
