'use strict';

angular.module('nummapApp')
    .directive('nmLogin', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/login.partial.html',
            scope: { loginText: '=ngModel'}
        };
    });