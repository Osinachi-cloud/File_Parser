#!/bin/bash

printf "\n"
### Get current working directory
BASEDIR=$(pwd)

echo "Supply directory path below: "
read -r pathDir

[[ $pathDir != /* ]] && pathDir="/$pathDir"

Dir=$BASEDIR$pathDir

if [ -d "$Dir" ]
then
    # create a file and add contents
    FILENAME="$Dir/$(Date | sed "s/ //g" | awk '{print tolower($0)}')-hicx.txt"
    touch "$FILENAME"

    {
      echo "HICX is a supplier data and information governance technology company"
      echo "with a mission to enable our customers to have reliable supplier data"
      echo "and information readily available, across all business functions, effortlessly."
    } >> "$FILENAME"

    printf "%s has been created\n" "$FILENAME"
else
    printf "Error: Directory %s does not exist\n" "$Dir"
    exit
fi



