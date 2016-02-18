#!/bin/bash
# =====================================================================
# Installation script for Christeam server.
# =====================================================================
#  * Install PostgreSQL 9.3
#  * Install JDK 8
#  * Install NodeJS (Bower, Grunt, Gulp, Polyserve)
#  * Linux boot service
#  * Create and initialize database
# =====================================================================
# INSTALL SOFTWARE REQUIREMENTS
# =====================================================================
# Install Postgresql 9.3 server
apt-get install postgresql-9.3
update-rc.d postgresql defaults
# Install JDK 8
add-apt-repository ppa:webupd8team/java
apt-get update
apt-get install oracle-java8-*
apt-get install maven
# Set environment variable for JDK 8.
echo '# System default JDK & JRE' >> /etc/bash.bashrc
echo 'export JAVA_HOME=/usr/lib/jvm/java-8-oracle' >> /etc/bash.bashrc
# Install NodeJS stack
apt-get install npm
apt-get install nodejs-legacy
npm install -g npm
npm install -g n
n stable
npm install -g bower grunt-cli gulp polyserve
# =====================================================================
# CONFIGURE DEPLOYMENT SPACE
# =====================================================================
USER=christeam				# Superuser of the application
ORG_NAME=digitaldefense			# Owner organization
APP_NAME=$USER				# Application name
BOOT_SCRIPT=$USER			# LSB boot service script
DB_NAME=$USER				# Database name
PASSWD=qwe123				# Administration password.
# Create application directory
mkdir /opt/$ORG_NAME
mkdir /opt/$ORG_NAME/$APP_NAME
# Create user for the application
useradd $USER
# Place deployments
cp ./christeam-server-$VERSION.jar /opt/$ORG_NAME/$APP_NAME/
cp ./$BOOT_SCRIPT /etc/init.d/
# Update roles and rights.
chmod 755 /etc/init.d/$BOOT_SCRIPT
chown -R $USER:$USER /opt/$ORG_NAME/$APP_NAME
# =====================================================================
# INITIALIZE FOR PRODUCTION
# =====================================================================
# Setup auto-boot
update-rc.d $BOOT_sCRIPT defaults
# Setup database roles.
su postgres -c "createuser $USER --createdb --echo --login --createrole --superuser --pwprompt=$PASSWD"
# Create DDL and setup initial data.
su postgres -c "createdb $DB_NAME --owner=$USER" 
su postgres -c "psql -d $DB_NAME -a -f ./init-db.sql"
su postgres -c "psql -d $DB_NAME -a -f ./generate-data.sql"
# =====================================================================
# FINISH INSTALLATION
# =====================================================================
reboot
