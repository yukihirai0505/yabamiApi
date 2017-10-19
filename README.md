## yabai API

### Instagram

#### User

- 1. To get user account info
    - https://api.yabaiwebyasan.com/v1/instagram/users/:accountName

ex) https://api.yabaiwebyasan.com/v1/instagram/users/i_do_not_like_fashion

- 2. To get user posts
    - https://api.yabaiwebyasan.com/v1/instagram/users/:userId/media/:afterCode

use `1. To get user account info` response `id` and `endCursor`. `endCursor` can be used as `afterCode`.

#### Tag

- 1. To get hashtag info
    - https://api.yabaiwebyasan.com/v1/instagram/tags/:tagName

ex) https://api.yabaiwebyasan.com/v1/instagram/tags/idonotlikefashion

- 2. To get user posts
    - https://api.yabaiwebyasan.com/v1/instagram/tags/:tagName/media/:afterCode

use `1. To get hashtag info` response `endCursor`. `endCursor` can be used as `afterCode`.
