'use strict';

angular.module('nummapApp')
    .directive('nmCompetencies', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/competencies.partial.html',
            scope: { competenciesSelectedArray: '=ngModel' },
            controller: function ($scope, Competencies) {
                Competencies.query(function(result) {
                    $scope.competenciesArray = result;
                });

                $scope.loadTags = function(query) {
                    var res = [];
                    $scope.competenciesArray.forEach(function(element) {
                        if (element.name.substr(0, query.length) === query) {
                            res.push(element);
                        }
                    });
                    return res;
                };
            }
        };
    });