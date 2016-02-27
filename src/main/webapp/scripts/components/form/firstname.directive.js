'use strict';

angular.module('nummapApp')
    .directive('nmFirstname', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/firstname.partial.html',
            scope: { firstnameText: '=ngModel' }
        };
    });