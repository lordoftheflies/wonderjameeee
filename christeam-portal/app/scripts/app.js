/**
 * Load controllers, directives, filters, services before bootstrapping the application.
 * NOTE: These are named references that are defined inside of the config.js RequireJS configuration file.
 */
define([
    'jquery',
    'angular',
    'main',
    'routes',
    'interceptors',
    'services'
], function ($, angular) {
    'use strict';

    /**
     * Application definition
     * This is where the AngularJS application is defined and all application dependencies declared.
     * @type {module}
     */
    var app = angular.module('app', [
        'app.routes',
        'app.interceptors',
        'app.services'
    ]);

    /**
     * Main Controller
     * This controller is the top most level controller that allows for all
     * child controllers to access properties defined on the $rootScope.
     */
//    predixApp.controller('MainCtrl', ['$scope', '$rootScope', 'UserService', function ($scope, $rootScope, predixUserService) {
    app.controller('MainCtrl', ['$scope', '$rootScope', function ($scope, $rootScope) {

            //Global application object
            window.App = $rootScope.App = {
                version: '1.0',
                name: 'Predix Seed',
                session: {},
                tabs: [
                    {icon: 'fa-tachometer', state: 'dashboards', label: 'Dashboards'},
                    {icon: 'fa-file-o', state: 'blankpage', label: 'Blank Page', subitems: [
                            {state: 'blanksubpage', label: 'Blank Sub Page'}
                        ]}
                ]
            };

            $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
                if (angular.isObject(error) && angular.isString(error.code)) {
                    switch (error.code) {
                        case 'UNAUTHORIZED':
                            //redirect
//                            predixUserService.login(toState);
                            break;
                        default:
                            //go to other error state
                    }
                } else {
                    // unexpected error
                }
            });
        }]);


    //Set on window for debugging
    window.application = app;

    //Return the application  object
    return app;
});
