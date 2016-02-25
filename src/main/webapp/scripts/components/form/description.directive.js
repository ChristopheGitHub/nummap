'use strict';

angular.module('nummapApp')
    .directive('nmDescription', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/description.partial.html',
            scope: { descriptionText: '=ngModel'}
        };
    });