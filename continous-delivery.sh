# Author: lordoftheflies (heglas11@gmail.com)
# Continous delivery script for Christeam project of DigitalDefense Ltd.

# Initialize continous-delivery process.

VERSION=1.6
DIR=/var/www/android

if [ -z "$CHRISTEAM_CD_ANDROID_DIR" ]; then
	echo "Create distribution ..."

	# Make distribution directory if not exists
	mkdir -p /var/www/android
	# Update privileges
	chown -R www-data:www-data /var/www/android

	echo "Update bash profile ..."

	# Add continous-delivery envrionment variable to user bash profile.
	echo "export CHRISTEAM_CD_ANDROID_DIR=${DIR}" | tee -a ~/.bashrc
	export CHRISTEAM_CD_ANDROID_DIR=${DIR}
	# Define current stable release.
	echo "export CHRISTEAM_CD_ANDROID_VERSION=${VERSION}" | tee -a ~/.bashrc
	export CHRISTEAM_CD_ANDROID_VERSION=$VERSION
	# Update session
	source ~/.bashrc
fi

echo "CONTINOUS-DELIVERY SETUP =================================="
echo "Distribution directory: ${CHRISTEAM_CD_ANDROID_DIR}"
echo "Current stable release: ${CHRISTEAM_CD_ANDROID_VERSION}"
echo "CONTINOUS-DELIVERY SETUP =================================="

# Update distribution from scm


# Create temporary directory.
mkdir -p ./tmp
cd ./tmp

# Download release.
curl -L -O -J -n -H "Accept:application/octet-stream" https://github.com/lordoftheflies/christeam/archive/${CHRISTEAM_CD_ANDROID_VERSION}.zip

# Post-process release.
unzip christeam-${CHRISTEAM_CD_ANDROID_VERSION}.zip
cd ./christeam-${CHRISTEAM_CD_ANDROID_VERSION}/ChristeamAppAndroid/app

# Copy to distribution
cp ./app-release.apk ${CHRISTEAM_CD_ANDROID_DIR}/christeamAppAndroid-${CHRISTEAM_CD_ANDROID_VERSION}.apk
chown -R www-data:www-data ${CHRISTEAM_CD_ANDROID_DIR}

ln -sf ${CHRISTEAM_CD_ANDROID_DIR}/christeam-app-android-${CHRISTEAM_CD_ANDROID_VERSION}.apk ${CHRISTEAM_CD_ANDROID_DIR}/christeam-app-android-stable.apk

# Clean up
cd ../../../../
rm -r ./tmp
