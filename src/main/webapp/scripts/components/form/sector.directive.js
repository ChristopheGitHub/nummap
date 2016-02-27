'use strict';

angular.module('nummapApp')
    .directive('nmSector', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/sector.partial.html',
            scope: { sectorArray: '=ngModel' },
            controller: function ($scope, Domains) {
                Domains.query(function(result) {
                    $scope.sectorArray = result;
                });
            }
        };
    });