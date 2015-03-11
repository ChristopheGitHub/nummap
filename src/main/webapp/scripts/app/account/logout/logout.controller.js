'use strict';

angular.module('nummapApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
