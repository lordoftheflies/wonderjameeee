
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




