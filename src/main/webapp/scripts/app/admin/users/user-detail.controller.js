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
        // $scope.user = {};
        $scope.load = function (login) {
            User.get({login: login}, function(result) {
                $scope.user = result;
            });
        };
        $scope.load($stateParams.login);

        $scope.categories = [
            {value: 'STUDENT', translationKey: 'register.form.category.student'},
            {value: 'PROFESSOR', translationKey: 'register.form.category.professor'},
            {value: 'FREELANCE', translationKey: 'register.form.category.freelance'},
            {value: 'COMPANY', translationKey: 'register.form.category.company'},
            {value: 'ASSOCIATION', translationKey: 'register.form.category.association'}
        ];

        $scope.fields = [
            {name: 'Outsourcing', value: 'OUTSOURCING'},
            {name: 'Consulting', value: 'CONSULTING'},
            {name: 'System Integration', value: 'SYSTEM_INTEGRATION'}
        ];
    });