'use strict';

angular.module('nummapApp')
    .directive('nmAddress', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/address.partial.html',
            scope: { addressText: '=ngModel'}
        };
    });