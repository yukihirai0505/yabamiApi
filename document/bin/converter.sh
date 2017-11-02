#!/bin/bash

cd `dirname $0`

files=()
files+=('../src/instagram_user.md')
files+=('../src/instagram_tag.md')
files+=('../src/instagram_media.md')
files+=('../src/instagram_comment.md')
files+=('../src/instagram_like.md')

echo 'FORMAT: 1A' > ../api.md || exit $?
cat ${files[@]} | sed -e '/^FORMAT: 1A/d' >> ../api.md || exit $?
mkdir -p ../output >>/dev/null
aglio -i ../api.md -o ../output/api.html || exit $?

exit 0
