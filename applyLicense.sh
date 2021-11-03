#!/bin/bash

for file in `find . -name "*.java"`
do
  echo "-- $file --"
  if ! grep -q Copyright $file
  then
    cat ./LICENSE $file >$file.new && mv $file.new $file
  else
		echo " ... skipped!"
  fi
done
