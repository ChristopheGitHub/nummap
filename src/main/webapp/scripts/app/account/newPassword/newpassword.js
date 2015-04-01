'use strict';

angular.module('nummapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('resetpassword', {
                parent: 'account',
                url: '/resetpassword',
                data: {
                    roles: [],
                    pageTitle: 'global.menu.account.resetpassword'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/newPassword/newpassword.html',
                        controller: 'ResetPasswordController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('password');
                        return $translate.refresh();
                    }]
                }
            });
    });
