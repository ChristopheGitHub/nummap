'use strict';

angular.module('nummapApp')
    .controller('MainController', function ($scope, $state, Principal, $http) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.markers = [];

        $scope.loadAll = function() {
            $http.get('api/markers', {})
                .success(function(data){
                    $scope.markers = data;
                    $scope.success = 'OK';
                });
        };
        $scope.loadAll();



        var tilesDict = {
            openstreetmap: {
                url: "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
                options: {
                    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                }
            }
        };


        angular.extend($scope, {
            center: {
                lat: 43.28520334369384,
                lng: -0.289764404296875,
                zoom: 9
            },
            markers:
              $scope.markers
            ,
            tiles: tilesDict.openstreetmap,
            defaults: {
                scrollWheelZoom: true,
                zoomControlPosition: 'bottomleft'
            }
        });
    });
