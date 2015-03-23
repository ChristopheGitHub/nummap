/**
 * Created by eisti on 3/17/15.
 */
/**
 * Created by eisti on 2/18/15.
 */
'use strict';

angular.module('nummapApp')
    .controller('UserDetailController', function ($scope, $stateParams, User) {
        $scope.user = {};
        $scope.load = function (login) {
            User.get({login: login}, function(result) {
                $scope.user = result;
            });
        };
        $scope.load($stateParams.login);
    });