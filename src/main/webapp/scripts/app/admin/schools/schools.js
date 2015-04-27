'use strict';

angular.module('nummapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('schools', {
                parent: 'admin',
                url: '/schools',
                data: {
                    roles: ['ROLE_ADMIN'] //// HERE
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/schools/schools.html',
                        controller: 'SchoolsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('school');
                        return $translate.refresh();
                    }]
                }
            })
    });