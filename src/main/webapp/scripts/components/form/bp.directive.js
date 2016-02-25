'use strict';

angular.module('nummapApp')
    .directive('nmBp', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/bp.partial.html',
            scope: { bpText: '=ngModel'}
        };
    });