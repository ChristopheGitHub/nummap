'use strict';

angular.module('nummapApp')
    .controller('MainController', function ($scope, $state, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        var tilesDict = {
            openstreetmap: {
                url: "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
                options: {
                    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                }
            }
        };

        console.log("eeeeeeee" + $state.current.name);

        angular.extend($scope, {
            center: {
                lat: 43.28520334369384,
                lng: -0.289764404296875,
                zoom: 9
            },
            tiles: tilesDict.openstreetmap,
            defaults: {
                scrollWheelZoom: false,
                zoomControlPosition: 'bottomleft'
            }
        });
    });
