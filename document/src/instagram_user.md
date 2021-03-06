# API Document

Response `endCursor` can be used as `afterCode`.

API Endpoint: https://api.yabaiwebyasan.com

# Group Instagram User

 Instagram User

## Get User Account Info [/v1/instagram/users/{accountName}]

### Get User Info API [GET]

#### Summary

* Get User Account Info

+ Parameters

    + accountName: i_do_not_like_fashion (string, required) - Instagram User Account Name

+ Response 200 (application/json)

    + Attributes
        + id: 6039196059 (string)
        + followedBy (object)
            + count: 167 (number)
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

## Get User Media [/v1/instagram/users/{userId}/media{?afterCode}]

### Get User Media API [GET]

#### Summary

* Get User Media

+ Parameters

    + userId: 6039196059 (string, required) - Instagram User Account Id
    + afterCode: hogehoge (string, optional) - endCursor

+ Response 200 (application/json)

    + Attributes
        + data (object)
            + user (object)
                + edgeOwnerToTimelineMedia (object)
                    + count: 21 (number)
                    + pageInfo (object)
                        + endCursor: hogehoge (string)
                        + hasNextPage: false (boolean)
                    + edges (array)
                        + (object)
                            + node (object)
                                + commentsDisabled: false (boolean)
                                + id: 1634458525801721216 (string)
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
                                + edgeMediaPreviewLike (object)
                                    + count: 2 (number)
                                + owner (object)
                                    + id: 6039196059 (string)
        + status: ok (string)
