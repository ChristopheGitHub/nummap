'use strict';

angular.module('nummapApp')
    .directive('nmComplement', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/complement.partial.html',
            scope: { complementText: '=ngModel'}
        };
    });