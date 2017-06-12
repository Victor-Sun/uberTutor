#!/bin/bash

git pull
git add .
read -p "Commit Message: " commit
git commit -m "$commit"
git push
