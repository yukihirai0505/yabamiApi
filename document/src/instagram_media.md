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
