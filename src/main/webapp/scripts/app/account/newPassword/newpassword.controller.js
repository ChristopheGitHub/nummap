'use strict';

angular.module('nummapApp')
    .controller('ResetPasswordController', function ($scope, $http) {

        $scope.resetPassword = function(loginoremail){
            $http.post('api/reset/'+loginoremail, {})
                .success(function(){
                    $scope.error = null;
                    $scope.success = 'OK';
                }).
                error(function(){
                    $scope.success = null;
                    $scope.error = 'ERROR';
                });
        }

    });
