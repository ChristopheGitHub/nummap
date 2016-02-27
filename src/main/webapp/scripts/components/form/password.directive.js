'use strict';

angular.module('nummapApp')
    .directive('nmPassword', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/password.partial.html',
            scope: { passwordText: '=ngModel'}
        };
    });