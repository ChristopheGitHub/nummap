'use strict';

angular.module('nummapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('csvimport', {
                parent: 'admin',
                url: '/csvimport',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'csvImport.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/csvImport/csvImport.html',
                        controller: 'csvImportController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('csvImport');
                        return $translate.refresh();
                    }]
                }
            });
    });
