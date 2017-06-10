#!/bin/bash

git pull
git add .
echo Enter commit message
read commit
git commit -m $commit
git push
