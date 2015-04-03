/**
 * Created by eisti on 3/17/15.
 */
'use strict';


angular.module('nummapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('users', {
                parent: 'admin',
                url: '/users',
                data: {
                    roles: ['ROLE_ADMIN'] //// HERE
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/users/users.html',
                        controller: 'UserController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('user');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userDetail', {
                parent: 'users',
                url: '/:login',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                templateUrl: 'scripts/app/admin/users/user-detail.html',
                controller: 'UserDetailController',
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('register');
                        return $translate.refresh();
                    }]
                },
                onEnter: ['$state', function($state) {
                  $(document).on('keyup', function(e) {
                    if(e.keyCode == 27) {
                      $(document).off('keyup');
                      $state.go('users');
                    }
                  });
             
                  $(document).on('click', '.Modal-backdrop, .Modal-holder', function() {
                    $state.go('users');
                  });
             
                  $(document).on('click', '.Modal-box, .Modal-box *', function(e) {
                    e.stopPropagation();
                  });
                }],
            });

    });
