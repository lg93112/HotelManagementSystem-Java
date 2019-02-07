#!/bin/bash
if [ ! -f .git/hooks/pre-commit ]; then
    git config --add checkstyle.jar  ./pre-commit/checkstyle-8.14-all.jar
    git config --add checkstyle.checkfile  ./pre-commit/google_checks.xml
    cp ./pre-commit/pre-commit .git/hooks/
    touch ./pre-commit/pre-commit
    echo "pre-commit has already been setup"
else
    echo "pre-commit set up success!"
fi
