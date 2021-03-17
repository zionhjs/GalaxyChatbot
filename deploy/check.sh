#!/bin/bash

HOME=/home/ec2-user

PULL=/home/ec2-user/jar

[[ ! -d $HOME ]] && sudo mkdir -p $HOME

[[ ! -d $PULL ]] && sudo mkdir -p $PULL

sudo chown ec2-user.ec2-user $HOME

