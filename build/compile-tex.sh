#!/bin/bash

if [ -z "$1" ]; then
  echo "Usage: $0 <tex-file>"
  exit 99
fi

SRCPATH=$1
DSTFILE=$(basename $SRCPATH .tex).pdf
DSTPATH=$(dirname $SRCPATH)/$DSTFILE

if [ ! -e "$SRCPATH" ]; then
  echo "Oops, srcfile doesn't exist"
  exit 10
fi

if [ -e "$DSTPATH" -a ! "$SRCPATH" -nt "$DSTPATH" ]; then
  echo "$DSTPATH is already up to date"
  exit 0
fi


TMPDIR=$(mktemp -d -p tmp/)

echo "Temporary folder is $TMPDIR"

echo "Convert $SRCPATH to $DSTFILE"

OPTS="-interaction=nonstopmode -file-line-error-style -output-directory  $TMPD"

pdflatex $OPTS $TMPDIR $SRCPATH
pdflatex $OPTS $TMPDIR $SRCPATH

cp $TMPDIR/$DSTFILE $DSTPATH
