#!/bin/bash

# Generiert eine Liste aller TODOS

echo "TODO Liste"
echo "=========="
echo
echo
echo "Letzter Update: "
date
echo

for FILE in $(find doc -name '*.tex'); do 
	echo $FILE
	grep --ignore-case \
		--line-number \
		TODO $FILE
	echo
done
