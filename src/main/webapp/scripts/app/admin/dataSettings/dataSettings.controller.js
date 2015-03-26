/**
 * Created by eisti on 2/18/15.
 */
'use strict';
angular.module('nummapApp')
    .controller('DataSettingsController', function ($scope, Domains, Competencies) {

        $scope.queryCompetence = '';
        $scope.querySector = '';

        $scope.domains = [];
        $scope.comptencies = [];

        $scope.loadAll = function() {
            Domains.query(function(result) {
                $scope.domains = result;
            });
            Competencies.query(function(result) {
                $scope.competencies = result;
            });
        };
        $scope.loadAll();

        $scope.createCompetence = function () {
            Competencies.save($scope.competence,
                function () {
                    $scope.loadAll();
                    $('#saveCompetenceModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.createDomain = function () {

            Domains.save($scope.domain,
                function () {
                    $scope.loadAll();
                    $('#saveDomainModal').modal('hide');
                    $scope.clear();
                });
        };


        $scope.updateCompetence = function (name) {
            $scope.competence = Competencies.get({name: name});
            $('#saveCompetenceModal').modal('show');
        };

        $scope.updateDomain = function (name) {
            $scope.domain = Domains.get({name: name});
            $('#saveDomainModal').modal('show');
        };

        $scope.deleteCompetence = function (name) {
            $scope.competence = Competencies.get({name: name});
            $('#deleteCompetenceConfirmation').modal('show');
        };

        $scope.deleteDomain = function (name) {
            $scope.domain = Domains.get({name: name});
            $('#deleteDomainConfirmation').modal('show');
        };


        $scope.confirmDeleteDomain = function (name) {
            Domains.delete({name: name},
                function () {
                    $scope.loadAll();
                    $('#deleteDomainConfirmation').modal('hide');
                    $scope.clear();
                });
        };


        $scope.confirmDeleteCompetence = function (name) {
            Competencies.delete({name: name},
                function () {
                    $scope.loadAll();
                    $('#deleteCompetenceConfirmation').modal('hide');
                    $scope.clear();
                });
        };


        $scope.clear = function () {
            $scope.domain={"name":null};
            $scope.competence = {"name":null};
        };

    });


