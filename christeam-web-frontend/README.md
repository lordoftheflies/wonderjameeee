# Christeam Web Frontend
This application created for Flavon as a Network Educational Software
Uses Plutonium Web Components and <a href="https://github.com/lordoftheflies/plutonium-library-design/" target="_blank">Plutonium UI Elements</a> inside an Angular application.

## To Run the application

### Get the source code
Make a directory for your project.  Clone or download and extract the seed in that directory.
```
git clone
```

### Install the dependencies
```
npm install
bower install
```

### Create a dist version
Use grunt to create a distribution version of your app, which will be located in the dist folder along with the nginx configuration files.  You will need to run this command during development every time before you cf push to make the latest dist.
```
grunt dist
```

## Setup services for your own development
Once you're ready to actually start developing, you'll need to setup your authentication & authorization properties.

# Copyright
Copyright &copy; 2015 Hegedűs László. All rights reserved.

The copyright to the computer software herein is the property of Hegedűs László. The software may be used and/or copied only with the written permission of Hegedűs László or in accordance with the terms and conditions stipulated in the agreement/contract under which the software has been supplied.


