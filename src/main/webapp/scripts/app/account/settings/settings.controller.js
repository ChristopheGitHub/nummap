'use strict';

angular.module('nummapApp')
    .controller('SettingsController', function ($scope, Principal, Auth) {
        $scope.success = null;
        $scope.error = null;
        Principal.identity().then(function(account) {
            $scope.settingsAccount = account;

            // Pour permettre l'affichage des listes de réseaux sociaux
            $scope.personSocialNetworkList = $scope.settingsAccount.personContactInformation.socialNetworkList ;
            if($scope.category === 'COMPANY') {
                $scope.companySocialNetworkList = $scope.settingsAccount.companyContactInformation.socialNetworkList ;
            }

            console.log($scope.settingsAccount);
        });



        $scope.save = function () {
            Auth.updateAccount($scope.settingsAccount).then(function() {
                $scope.error = null;
                $scope.success = 'OK';
                Principal.identity().then(function(account) {
                    $scope.settingsAccount = account;
                });
            }).catch(function() {
                $scope.success = null;
                $scope.error = 'ERROR';
            });
        };
    });
