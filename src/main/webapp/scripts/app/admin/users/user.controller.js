/**
 * Created by eisti on 2/18/15.
 */
'use strict';
angular.module('nummapApp')
    .controller('UserController', function ($scope, User) {
        $scope.users = [];
        $scope.loadAll = function() {
            User.query(function(result) {
                $scope.users = result;
            });
        };
        $scope.loadAll();


        $scope.delete = function (login) {
            $scope.user = User.get({login: login});
            console.log($scope.user);
            $('#deleteUserConfirmation').modal('show');
        };

        $scope.confirmDelete = function (login) {
            User.delete({login: login},
                function () {
                    $scope.loadAll();
                    $('#deleteUserConfirmation').modal('hide');
                    $scope.clear();
                });
        };


        $scope.clear = function () {
            $scope.user = {"createdBy":null,"createdDate":null,"lastModifiedBy":null,"lastModifiedDate":null,"id":null,"login":null,"email":null,"category":null,"description":null,"raisonSociale":null,"personContactInformation":null,"companyContactInformation":null,"competencies":null,"sectors":null,"fields":null,"customers":null,"activated":true,"langKey":null,"activationKey":null};
        };

        /* Fonction à implémenter */
        /*
         $scope.create = function () {
         Author.save($scope.author,
         function () {
         $scope.loadAll();
         $('#saveAuthorModal').modal('hide');
         $scope.clear();
         });
         };

         $scope.update = function (id) {
         $scope.author = Author.get({id: id});
         $('#saveAuthorModal').modal('show');
         };
        */
     });

