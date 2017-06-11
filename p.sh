#!/bin/bash

read -p "Commit Message: " commit
git pull
git add.
git commit -m "$commit"
git push
