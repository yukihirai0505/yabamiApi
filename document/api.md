FORMAT: 1A
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
# Group Instagram Media

 Instagram Media


## Get Media Info [/v1/instagram/media/shortcode/{shortcode}]

### Get Media Info API [GET]

#### Summary

* Get Media Info

+ Parameters

    + shortcode: Bauw16WhS2A (string, required) - Instagram shortcode

+ Response 200 (application/json)

    + Attributes
        + shortcodeMedia (object)
            + id: 1634458525801721216 (string)
            + shortcode: Bauw16WhS2A (string)
            + dimensions (object)
                + height: 1080 (number)
                + width: 1080 (number)
            + mediaPreview (string)
            + displayUrl: https://hogehoge.com (string)
            + isVideo: false (boolean)
            + shouldLogClientEvent: false (boolean)
            + trackingToken (string)
            + edgeMediaToTaggedUser (object)
                + edges (array)
                    + (object)
                        + node (object)
                            + user (object)
                                + username (string)
                            + x: 0.703125 (number)
                            + y: 0.9546875 (number)
            + captionIsEdited: false (boolean)
            + edgeMediaToComment (object)
                + count: 3 (number)
                + pageInfo (object)
                    + endCursor: hogehoge (string)
                    + hasNextPage: true (boolean)
                + edges (array)
                    + (object)
                        + node (object)
                            + id (string)
                            + text (string)
                            + createdAt (number)
                            + owner (object)
                                + id (string)
                                + profilePicUrl (string)
                                + username (string)
            + commentsDisabled: false (boolean)
            + takenAtTimestamp: 1509062661 (number)
            + edgeMediaPreviewLike (object)
                + count: 3 (number)
                + edges (array)
                    + (object)
                        + node (object)
                            + id (string)
                            + profilePicUrl (string)
                            + username (string)
            + owner (object)
                + id (string)
                + profilePicUrl (string)
                + username (string)
                + blockedByViewer: false (boolean)
                + followedByViewer: false (boolean)
                + fullName (string)
                + hasBlockedViewer: false (boolean)
                + isPrivate: false (boolean)
                + isUnpublished: false (boolean)
                + isVerified: false (boolean)
                + requestedByViewer: false (boolean)
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
