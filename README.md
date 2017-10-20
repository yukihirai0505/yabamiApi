## yabai API

### Instagram

Response `endCursor` can be used as `afterCode`.

#### User

- 1. To get user account info
    - https://api.yabaiwebyasan.com/v1/instagram/users/:accountName

ex) https://api.yabaiwebyasan.com/v1/instagram/users/i_do_not_like_fashion

- 2. To get user posts
    - https://api.yabaiwebyasan.com/v1/instagram/users/:userId/media

query option: afterCode

#### Tag

- 1. To get hashtag info
    - https://api.yabaiwebyasan.com/v1/instagram/tags/:tagName

ex) https://api.yabaiwebyasan.com/v1/instagram/tags/idonotlikefashion

- 2. To get user posts
    - https://api.yabaiwebyasan.com/v1/instagram/tags/:tagName/media

query option: afterCode

#### Media

- 1. To get media info
    - https://api.yabaiwebyasan.com/v1/instagram/media/shortcode/:shortcode

ex) https://api.yabaiwebyasan.com/v1/instagram/media/shortcode/BaczO1-BOdy

#### Comment

- 1. To get media comments
    - https://api.yabaiwebyasan.com/v1/instagram/media/shortcode/:shortcode/comments

query option: afterCode

#### Like

- 1. To get media likes
    - https://api.yabaiwebyasan.com/v1/instagram/media/shortcode/:shortcode/likes

query option: afterCode

