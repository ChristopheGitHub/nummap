'use strict';

angular.module('nummapApp')
    .directive('nmName', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/name.partial.html',
            scope: { nameText: '=ngModel'}
        };
    });