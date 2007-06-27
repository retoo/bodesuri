#!/bin/bash
# Das ganze Prozedere muss nur einmal gemacht werden. Das Passwort gehört nachher ins Property File

set -e

KEYTOOL=$(which keytool)
KEYSTORE="bodesuri.keystore"

if [ $# -ne 1 ]; then
  echo "usage: $0 <pass>" 
  exit 99
fi


PASS=$1

echo "pass: '$PASS'"

rm -f $KEYSTORE

$KEYTOOL -genkey \
        -validity $[365*4] \
        -alias bodesuri \
        -keystore $KEYSTORE \
        -storepass $PASS \
        -keypass $PASS \
        -dname 'CN=Bodesuri, O=Bodesuri, L=Rapperswil, C=CH'

$KEYTOOL -selfcert \
        -validity $[365*4] \
        -alias bodesuri \
        -keystore $KEYSTORE \
        -storepass $PASS \
        -keypass $PASS 
