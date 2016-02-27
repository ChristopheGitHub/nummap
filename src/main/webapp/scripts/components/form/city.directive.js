'use strict';

angular.module('nummapApp')
    .directive('nmCity', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/city.partial.html',
            scope: { cityText: '=ngModel'}
        };
    });