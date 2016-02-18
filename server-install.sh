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
# Create user for the application
mkdir /opt/$ORG_NAME
mkdir /opt/$ORG_NAME/$APP_NAME
useradd $USER
cp ./$BOOT_SCRIPT /etc/init.d/
chmod 755 /etc/init.d/$BOOT_SCRIPT
chown -R $USER:$USER /opt/$ORG_NAME/$APP_NAME
# =====================================================================
# INITIALIZE FOR PRODUCTION
# =====================================================================
update-rc.d $BOOT_sCRIPT defaults
su postgres -c "createuser $USER --createdb --echo --login --createrole --superuser --pwprompt=$PASSWD"
su postgres -c "createdb $DB_NAME --owner=$USER" 
su postgres -c "psql -d $DB_NAME -a -f ./init-db.sql"
su postgres -c "psql -d $DB_NAME -a -f ./generate-data.sql"


