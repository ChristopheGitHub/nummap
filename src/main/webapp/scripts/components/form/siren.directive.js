'use strict';

angular.module('nummapApp')
    .directive('nmSiren', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/siren.partial.html',
            scope: { sirenText: '=ngModel'}
        };
    });