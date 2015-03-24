/**
 * Created by eisti on 3/17/15.
 */


'use strict';


angular.module('nummapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dataSettings', {
                parent: 'admin',
                url: '/dataSettings',
                data: {
                    roles: ['ROLE_ADMIN'] //// HERE
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/dataSettings/dataSettings.html',
                        controller: 'DataSettingsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dataSettings');
                        return $translate.refresh();
                    }]
                }
            })
    });

