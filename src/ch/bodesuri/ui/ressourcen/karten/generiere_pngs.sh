#!/bin/bash

for svg in *.svg; do
	name=`basename $svg .svg`
	png=$name.png
	rsvg -w 80 -h 100 $svg $png
	echo $png generiert.
done
