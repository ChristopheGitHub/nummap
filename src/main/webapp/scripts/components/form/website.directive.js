'use strict';

angular.module('nummapApp')
    .directive('nmWebsite', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/website.partial.html',
            scope: { websiteText: '=ngModel'}
        };
    });