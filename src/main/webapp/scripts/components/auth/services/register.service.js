'use strict';

angular.module('nummapApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


