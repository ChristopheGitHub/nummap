'use strict';

angular.module('nummapApp')
    .directive('nmPhone', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/phone.partial.html',
            scope: { phoneText: '=ngModel'}
        };
    });