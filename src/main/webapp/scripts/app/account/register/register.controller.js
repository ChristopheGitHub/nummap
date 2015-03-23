'use strict';

angular.module('nummapApp')
    .controller('RegisterController', function ($scope, $translate, $timeout, Auth) {
        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.registerAccount = {};
        $timeout(function (){angular.element('[ng-model="registerAccount.login"]').focus();});

        $scope.personSocialNetworkList = [];
        $scope.companySocialNetworkList = [];
        
  
        $scope.addElement = function(list) {
            // $scope.socialNetworkList.push({});
            list.push({});
            // console.log($scope.socialNetworkList);
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
                $scope.registerAccount.PersonContactInformation.socialNetworkList = $scope.personSocialNetworkList ;
                if($scope.category === 'COMPANY') {
                    $scope.registerAccount.CompanyContactInformation.socialNetworkList = $scope.companySocialNetworkList ;
                }

                Auth.createAccount($scope.registerAccount).then(function () {
                    $scope.success = 'OK';
                }).catch(function (response) {
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                });
            }
        };
    });
