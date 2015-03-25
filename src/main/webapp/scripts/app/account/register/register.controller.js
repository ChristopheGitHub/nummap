'use strict';

angular.module('nummapApp')
    .controller('RegisterController', function ($scope, $translate, $timeout, Auth, Domains, Competencies) {
        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.registerAccount = {};
        $timeout(function (){angular.element('[ng-model="registerAccount.login"]').focus();});

        $scope.personSocialNetworkList = [];
        $scope.companySocialNetworkList = [];

        $scope.domainsSelected = [];
        $scope.competenciesSelected = [];

        Domains.query(function(result) {
            $scope.sectors = result;
            console.log($scope.sectors);
        });
        Competencies.query(function(result) {
            $scope.competencies = result;
            console.log($scope.competencies);
        });

        $scope.fields = [
            {name: 'Outsourcing'},
            {name: 'Consulting'},
            {name: 'System Integration'}
        ];



        $scope.addElement = function(list) {
            list.push({});
            console.log(list);
        };

        $scope.register = function () {
            if ($scope.registerAccount.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                $scope.registerAccount.langKey = $translate.use();
                $scope.doNotMatch = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;

                // Ajout de la liste de réseaux sociaux
                // $scope.registerAccount.PersonContactInformation.socialNetworkList = $scope.personSocialNetworkList ;
                // if($scope.category === 'COMPANY') {
                //     $scope.registerAccount.CompanyContactInformation.socialNetworkList = $scope.companySocialNetworkList ;
                // }

                // Ajout de la liste des domaines
                $scope.registerAccount.sectors = [];
                $scope.sectors.forEach(function(element) {
                    if (element.checked) {
                        $scope.registerAccount.sectors.push({name : element.name});
                    }
                });

                // Ajout de la liste des domaines
                $scope.registerAccount.fields = [];
                $scope.fields.forEach(function(element) {
                    if (element.checked) {
                        $scope.registerAccount.sectors.push({name : element.name});
                    }
                });

                console.log($scope.registerAccount.sectors);
                console.log($scope.registerAccount.fields);
                // console.log($scope.registerAccount.sectors);

                // Auth.createAccount($scope.registerAccount).then(function () {
                //     $scope.success = 'OK';
                // }).catch(function (response) {
                //     $scope.success = null;
                //     if (response.status === 400 && response.data === 'login already in use') {
                //         $scope.errorUserExists = 'ERROR';
                //     } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                //         $scope.errorEmailExists = 'ERROR';
                //     } else {
                //         $scope.error = 'ERROR';
                //     }
                // });
            }
        };
    });
