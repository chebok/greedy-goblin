#!/bin/bash

KCHOST=http://localhost:8080
REALM=greedy-goblin
CLIENT_ID=greedy-goblin-service
UNAME=test
PASSWORD=otus

ACCESS_TOKEN=`curl \
  -d "client_id=$CLIENT_ID" \
  -d "username=$UNAME" \
  -d "password=$PASSWORD" \
  -d "grant_type=password" \
  "$KCHOST/realms/$REALM/protocol/openid-connect/token"  | jq -r '.access_token'`
echo "$ACCESS_TOKEN"
